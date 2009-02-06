DESCRIPTION = "default web content"
SECTION = "base"
PRIORITY = "required"
PR = "r1"
LICENSE = "GPL"

SRC_URI = " \
  file://index.html \   
  file://warranty.html \   
  file://brd.jpg \   
  file://ws.jpg \   
  file://gumlogo.gif \   
  file://ifconfig \   
  "

S = "${WORKDIR}"

do_install () {

  install -m 0755 -d ${D}${localstatedir}/www/
  install -m 0755 -d ${D}${libdir}/cgi-bin

	install -m 0655 ${WORKDIR}/*.html   ${D}${localstatedir}/www/
	install -m 0655 ${WORKDIR}/*.jpg    ${D}${localstatedir}/www/
	install -m 0655 ${WORKDIR}/*.gif    ${D}${localstatedir}/www/
	install -m 0655 ${WORKDIR}/ifconfig ${D}${libdir}/cgi-bin

}

PACKAGES = "${PN}"
FILES_${PN} = "/*"

