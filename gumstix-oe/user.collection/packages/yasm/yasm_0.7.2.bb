DESCRIPTION = "yasm"
HOMEPAGE = "http://www.tortall.net/projects/yasm/"
LICENSE = "BSD and LGPL"

PR = "r0"

SRC_URI = " \
    http://www.tortall.net/projects/yasm/releases/yasm-${PV}.tar.gz \
    "

inherit autotools

