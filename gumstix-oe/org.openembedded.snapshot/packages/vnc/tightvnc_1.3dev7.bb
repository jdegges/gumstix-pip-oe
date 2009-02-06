DESCRIPTION = "A Unix VNC client"
HOMEPAGE = "http://www.tightvnc.com/"
DEPENDS = "virtual/libx11 zlib libxmu libxaw"
LICENSE = "GPL"

SRC_URI = "${SOURCEFORGE_MIRROR}/vnc-tight/tightvnc-1.3dev7_unixsrc.tar.gz \
           file://Makefile"

S = "${WORKDIR}/vnc_unixsrc/vncviewer/"

PACKAGES = "tightvncviewer-dbg tightvncviewer"
FILES_tightvncviewer = "/usr/bin/tightvncviewer"

do_compile () {
	install ${WORKDIR}/Makefile ${S}
	oe_runmake
}

do_install () {
	install -d ${D}${bindir}
	install tightvncviewer ${D}${bindir}
}

