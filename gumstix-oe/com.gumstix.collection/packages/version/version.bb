DESCRIPTION = "build information"
SECTION = "base"
PRIORITY = "required"
PR = "r1"
LICENSE = "GPL"

do_install() {
	install -d ${D}${sysconfdir}
	echo "Revision `svnversion ${GUMSTIXTOP}`" >  ${D}${sysconfdir}/gumstix-version
  echo "Built on `date`"                     >> ${D}${sysconfdir}/gumstix-version
  echo "Build machine: `hostname`"           >> ${D}${sysconfdir}/gumstix-version
  echo "Target machine: ${MACHINE}"          >> ${D}${sysconfdir}/gumstix-version
  echo "libc: ${ANGSTROM_MODE}"              >> ${D}${sysconfdir}/gumstix-version
}

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

