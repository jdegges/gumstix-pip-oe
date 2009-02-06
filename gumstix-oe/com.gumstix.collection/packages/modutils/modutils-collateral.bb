SECTION = "base"
DESCRIPTION = "modutils configuration files"
PR = "r5"
LICENSE = "MIT"

SRC_URI = " \
           file://modules \
	         file://modules.conf \
	         file://ethernet \
	         file://ethernet.conf \
          "

do_compile () {
}

do_install () {

	if [-s ${WORKDIR}/modules] ; then
    install -d ${D}${sysconfdir}
	  install -m 0644 ${WORKDIR}/modules ${D}${sysconfdir}/modules
  fi

  if [ ${MAJOR_KERNEL_VERSION}=2.6 ]; then

    install -d ${D}${sysconfdir}/modprobe.d
    install -m 0644 ${WORKDIR}/ethernet.conf ${D}${sysconfdir}/modprobe.d/

    install -m 0755 -d ${D}${sysconfdir}/modutils
    install -m 0644 ${WORKDIR}/ethernet ${D}${sysconfdir}/modutils/

  else
	    install -m 0644 ${WORKDIR}/modules.conf ${D}${sysconfdir}/modules.conf
  fi

}

FILES_${PN} = "${sysconfdir}/modprobe.d/"
FILES_${PN} += "${sysconfdir}/modutils/"

CONFFILES_${PN} = "${sysconfdir}/modprobe.d/ethernet.conf"
CONFFILES_${PN} += "${sysconfdir}/modutils/ethernet"

