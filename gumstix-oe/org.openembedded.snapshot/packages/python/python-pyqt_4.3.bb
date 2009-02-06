DESCRIPTION = "Python Qt4 Bindings"
HOMEPAGE = "http://riverbankcomputing.co.uk"
AUTHOR = "Phil Thomson @ riverbank.co.uk"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "GPL"
RDEPENDS = "python-core"
SRCNAME = "pyqt"
PR = "ml0"

SRC_URI = "http://www.riverbankcomputing.com/Downloads/PyQt4/GPL/PyQt-x11-gpl-${PV}.tar.gz \
           file://cross-compile.patch;patch=1"

BROKEN = "1"
# Something really fishy wrt. to arm/mips/etc. double vs. qreal. May even be a problem in Qt headers itself.
# Symptons:
#| sipQtCoreQTimeLine.cpp:136: error: conflicting return type specified for 'virtual double sipQTimeLine::valueForTime(int) const'
#| /home/pkg/oe/fic-gta01/tmp/staging/arm-angstrom-linux-gnueabi/qt4/include/QtCore/qtimeline.h:92: error:   overriding 'virtual qreal QTimeLine::valueForTime(int) const'
# And:
#| sipQtCoreQRectF.cpp: In function 'PyObject* meth_QRectF_getRect(PyObject*, PyObject*)':
#| sipQtCoreQRectF.cpp:1182: error: no matching function for call to 'QRectF::getRect(double*, double*, double*, double*)'
#| /home/pkg/oe/fic-gta01/tmp/staging/arm-angstrom-linux-gnueabi/qt4/include/QtCore/qrect.h:725: note: candidates are: void QRectF::getRect(qreal*, qreal*, qreal*, qreal*) const
#| sipQtCoreQRectF.cpp: In function 'PyObject* meth_QRectF_getCoords(PyObject*, PyObject*)':
#| sipQtCoreQRectF.cpp:1237: error: no matching function for call to 'QRectF::getCoords(double*, double*, double*, double*)'
#| /home/pkg/oe/fic-gta01/tmp/staging/arm-angstrom-linux-gnueabi/qt4/include/QtCore/qrect.h:741: note: candidates are: void QRectF::getCoords(qreal*, qreal*, qreal*, qreal*) const
#| make[1]: *** [sipQtCoreQRectF.o] Error 1

S = "${WORKDIR}/PyQt-x11-gpl-${PV}"

inherit qmake qt4x11 sip distutils-base

PARALLEL_MAKE = ""

QMAKE_PROFILES = "pyqt.pro"
EXTRA_SIPTAGS = "-tWS_X11 -tQt_4_3_0 -xVendorID -xPyQt_SessionManager -xPyQt_Accessibility"
EXTRA_OEMAKE = " MAKEFLAGS= "

SIP_MODULES = "QtCore QtGui QtNetwork QtSql QtSvg QtXml"
# SIP_MODULES += "QtAssistant"
EXTRA_QMAKEVARS_POST += "INCLUDEPATH+=${OE_QMAKE_INCDIR_QT}/Qt \
                         INCLUDEPATH+=${STAGING_INCDIR}/${PYTHON_DIR}"

#EXTRA_QMAKEVARS_POST += "QMAKE_UIC=${STAGING_BINDIR_NATIVE}/uic \
#                         QMAKE_MOC=${STAGING_BINDIR_NATIVE}/moc \
#                         QMAKE_RPATH=-Wl,-rpath-link, \
#                         DESTDIR= \
#                         VERSION=1.0.0 \
#                         DEFINES+=SIP_MAKE_DLL \
#                         DEFINES+=SIP_QT_SUPPORT \
#                         INCLUDEPATH+=. \
#                         INCLUDEPATH+=${STAGING_INCDIR}/${PYTHON_DIR} \
#                         INCLUDEPATH+=${STAGING_INCDIR} \
#                         LIBS+=-L${STAGING_LIBDIR}/${PYTHON_DIR}/site-packages"

do_configure_prepend() {
    echo -e "TEMPLATE=subdirs\nSUBDIRS=${SIP_MODULES}\n" >pyqt.pro
}

#do_configure() {
#    echo "yes" | python configure.py -w -q ${OE_QMAKE_QMAKE}
#}

do_stage() {
    install -d ${STAGING_SIPDIR}/qt/
    install -d ${STAGING_LIBDIR}/${PYTHON_DIR}/site-packages
    for module in ${SIP_MODULES}
    do
        install -m 0644 ${S}/sip/${module}/*.sip ${STAGING_SIPDIR}/qt/
	install -m 0755 ${module}/lib${module}.so ${STAGING_LIBDIR}/${PYTHON_DIR}/site-packages/${module}.so
    done
}

do_install() {
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt4
    for module in ${SIP_MODULES}
    do
		echo "from PyQt4.${module} import *\n" >> ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt4/Qt.py
		install -m 0755 ${module}/lib${module}.so ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt4/${module}.so
    done
	cp -pPR elementtree ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt4/
	cp __init__.py ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt4/
}

FILES_${PN} = "${libdir}/${PYTHON_DIR}/site-packages"
