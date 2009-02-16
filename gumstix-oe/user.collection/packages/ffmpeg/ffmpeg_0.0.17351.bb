DESCRIPTION = "ffmpeg"
HOMEPAGE = "http://www.ffmpeg.org/"
LICENSE = "GNU Library or Lesser General Public License (LGPL)"
SECTION = "libs"
PRIORITY = "optional"

PR = "r0"

DEPENDS = "yasm"

SRC_URI = " \
    http://jdegges.googlepages.com/ffmpeg-${PV}.tar.gz \
    "
inherit autotools

EXTRA_OECONF = " \
    --enable-shared \
    --bindir=/usr/bin \
    --datadir=/usr/share \
    --libdir=/usr/lib \
    --shlibdir=/usr/lib \
    --mandir=/usr/share/man \
    --cpu=${PACKAGE_ARCH} \
    --arch=${PACKAGE_ARCH} \
    --enable-pthreads \
    --cross-prefix=${TARGET_PREFIX} \
    --prefix=${STAGING_DIR} \
    --enable-gpl \
    --enable-cross-compile \
    "

oe_runconf () {
    if [ -x ${S}/configure ] ; then
        cfgcmd="${S}/configure \
                ${EXTRA_OECONF} \
                $@"
        oenote "Running $cfgcmd..."
        $cfgcmd || oefatal "oe_runconf failed"
    else
        oefatal "no configure script found"
    fi
}

