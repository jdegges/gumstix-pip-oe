PR = "r9"

SRC_URI = "${GNU_MIRROR}/ncurses/ncurses-${PV}.tar.gz \
	   file://visibility.patch;patch=1"
S = "${WORKDIR}/ncurses-${PV}"

require ncurses.inc
