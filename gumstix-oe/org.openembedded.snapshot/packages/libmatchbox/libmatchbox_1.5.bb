require libmatchbox.inc

PR = "r1"

SRC_URI = "http://projects.o-hand.com/matchbox/sources/${PN}/${PV}/${PN}-${PV}.tar.gz \
	   file://check.m4 \
	   file://autofoo.patch;patch=1"

do_configure_prepend () {
	mv ${WORKDIR}/check.m4 ${S}/
}
