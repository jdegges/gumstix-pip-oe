DESCRIPTION = "Evas is a hardware-accelerated canvas API that can draw \
anti-aliased text, smooth super and sub-images, alpha-blend, as well as drop \
down to using normal X11 primitives such as pixmaps, lines and rectangles if \
your CPU or graphics hardware are too slow."
LICENSE = "MIT"
# can also depend on valgrind (?)
DEPENDS = "freetype libpng jpeg eet"
PROVIDES += "virtual/evas"

inherit efl lib_package

export FREETYPE_CONFIG = "${STAGING_BINDIR_CROSS}/freetype-config"

SRC_URI = "http://enlightenment.freedesktop.org/files/evas-${PV}.tar.gz \
           file://fix-configure.patch;patch=1 \
           ${E_CVS};module=e17/libs/evas/m4;date=20060101"
#	   file://pagesize.patch;patch=1 \

S = "${WORKDIR}/evas-${PV}"
FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/evas"

EXTRA_OECONF = "<override me>"

do_configure_prepend() {
	if [ -e "${WORKDIR}/m4" ]; then
		install -d "${S}/m4"
		install "${WORKDIR}/m4/"*.m4 "${S}/m4"
		aclocal -I m4
	fi
}

#do_install_append() {
#    find "${S}" -name .debug -type d | xargs -iBLAH rm -rf "BLAH"
#}

do_stage_append() {
	cd src
	modules=`find modules -name ".libs"`
	for module in $modules
	do
		install -d ${STAGING_LIBDIR}/evas/`dirname $module`/${TARGET_OS}-gnu-${TARGET_ARCH}
		install -m 0755 $module/module.so ${STAGING_LIBDIR}/evas/`dirname $module`/${TARGET_OS}-gnu-${TARGET_ARCH}/module.so
	done
}

headers = "<override me>"
libraries = "libevas"
FILES_${PN} += "/usr/lib/evas/modules/*/*/*/*.so"
FILES_${PN}-dev += "/usr/lib/evas/modules/*/*/*/*.a /usr/lib/evas/modules/*/*/*/*.la"
FILES_${PN}-dbg += "/usr/lib/evas/modules/*/*/*/.debug/"
