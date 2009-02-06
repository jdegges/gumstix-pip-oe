DESCRIPTION = "Qtopia4 hello world program"
PR = "r0"

SRC_URI = " \
  file://HelloQtConsole.cpp \
  file://HelloQtConsole.pro \
 "

QMAKE_PROFILES="HelloQtConsole.pro"

do_configure_prepend() {
  cp ${WORKDIR}/*.pro ${S}/
  cp ${WORKDIR}/*.cpp ${S}/
}

do_compile() {
	oe_runmake
}

do_install () {
  install -d ${D}${bindir}/
	install -m 0755 ${S}/HelloQtConsole ${D}${bindir}/
}

PACKAGES = "${PN}"
FILES_${PN} = "${bindir}/*"

inherit qtopia4core
