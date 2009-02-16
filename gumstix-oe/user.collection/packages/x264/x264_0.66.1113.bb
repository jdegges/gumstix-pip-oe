DESCRIPTION = "x264"
HOMEPAGE = "http://www.videolan.org/developers/x264.html"
LICENSE = "GPL"

PR = "r0"

DEPENDS = "yasm"

SRC_URI = " \
    http://jdegges.googlepages.com/x264-${PV}.tar.gz \
    "

inherit autotools

EXTRA_OECONF = " \
    --enable-asm \
    --enable-shared \
    "

