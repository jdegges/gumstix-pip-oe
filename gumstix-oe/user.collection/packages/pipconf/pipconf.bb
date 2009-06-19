DESCRIPTION = "PIP Configuration Files"
SECTION = "misc"
LICENSE = "MIT"

SRC_URI = "file://S43wireless.sh "

do_install_append() {
    install -m 0755 ${WORKDIR}/S43wireless.sh ${D}/etc/rcS.d/
}

FILES_${PN} += "${D}/etc/rcS.d/S43wireless.sh "
