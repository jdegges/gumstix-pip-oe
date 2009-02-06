SECTION = "console/network"
require mysql_${PV}.bb
inherit native
PR="r3"

RDEPENDS_${PN} = ""

PACKAGES = ""
DEPENDS = "ncurses-native"
EXTRA_OEMAKE = ""
EXTRA_OECONF = " --with-embedded-server "

do_stage_append() {
	install -m 0755 sql/gen_lex_hash ${STAGING_BINDIR}/
}

do_install() {
	:
}
