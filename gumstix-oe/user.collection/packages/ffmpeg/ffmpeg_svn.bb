DESCRIPTION = "ffmpeg"
HOMEPAGE = "http://www.ffmpeg.org/"
LICENSE = "GNU Library or Lesser General Public License (LGPL)"
SECTION = "libs"
PRIORITY = "optional"
PV = "19220"
PR = "r1"

DEPENDS = "zlib"

DEFAULT_PREFERENCE = "-1"

SRC_URI = "svn://svn.ffmpeg.org/ffmpeg/;module=trunk;rev=${PV} "

S = "${WORKDIR}/trunk"

inherit autotools pkgconfig

EXTRA_OECONF = "                    \
    --enable-shared                 \
    --enable-pthreads               \
    --enable-gpl                    \
                                    \
    --disable-debug                 \
    --disable-ffserver              \
    --disable-ffplay                \
    --disable-stripping             \
                                    \
    --enable-cross-compile          \
    --cross-prefix=${TARGET_PREFIX} \
    --cpu=${PACKAGE_ARCH}           \
    --arch=${PACKAGE_ARCH}          \
    --prefix=${STAGING_DIR}         \
                                    \
    --bindir=/usr/bin               \
    --datadir=/usr/share            \
    --libdir=/usr/lib               \
    --incdir=/usr/include           \
    --shlibdir=/usr/lib             \
    --mandir=/usr/share/man         \
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

do_stage() {
    oe_libinstall -a -so -C libavcodec libavcodec ${STAGING_LIBDIR}
    oe_libinstall -a -so -C libavdevice libavdevice ${STAGING_LIBDIR}
    oe_libinstall -a -so -C libavformat libavformat ${STAGING_LIBDIR}
    oe_libinstall -a -so -C libavutil libavutil ${STAGING_LIBDIR}
    oe_libinstall -a -so -C libswscale libswscale ${STAGING_LIBDIR}

    install -d ${STAGING_INCDIR}/libavcodec
    install -m 0644 ${S}/libavcodec/avcodec.h \
        ${STAGING_INCDIR}/libavcodec/avcodec.h
    install -m 0644 ${S}/libavcodec/opt.h \
        ${STAGING_INCDIR}/libavcodec/opt.h
    install -m 0644 ${S}/libavcodec/vdpau.h \
        ${STAGING_INCDIR}/libavcodec/vdpau.h
    install -m 0644 ${S}/libavcodec/xvmc.h \
        ${STAGING_INCDIR}/libavcodec/xvmc.h

    install -d ${STAGING_INCDIR}/libavdevice
    install -m 0644 ${S}/libavdevice/avdevice.h \
        ${STAGING_INCDIR}/libavdevice/avdevice.h

    install -d ${STAGING_INCDIR}/libavformat
    install -m 0644 ${S}/libavformat/avformat.h \
        ${STAGING_INCDIR}/libavformat/avformat.h
    install -m 0644 ${S}/libavformat/avio.h \
        ${STAGING_INCDIR}/libavformat/avio.h
    install -m 0644 ${S}/libavformat/rtspcodes.h \
        ${STAGING_INCDIR}/libavformat/rtspcodes.h
    install -m 0644 ${S}/libavformat/rtsp.h \
        ${STAGING_INCDIR}/libavformat/rtsp.h


    install -d ${STAGING_INCDIR}/libavutil
    install -m 0644 ${S}/libavutil/adler32.h \
        ${STAGING_INCDIR}/libavutil/adler32.h
    install -m 0644 ${S}/libavutil/avstring.h \
        ${STAGING_INCDIR}/libavutil/avstring.h
    install -m 0644 ${S}/libavutil/avutil.h \
        ${STAGING_INCDIR}/libavutil/avutil.h
    install -m 0644 ${S}/libavutil/base64.h \
        ${STAGING_INCDIR}/libavutil/base64.h
    install -m 0644 ${S}/libavutil/common.h \
        ${STAGING_INCDIR}/libavutil/common.h
#    install -m 0644 ${S}/libavutil/crc.h \
#        ${STAGING_INCDIR}/libavutil/crc.h \
    install -m 0644 ${S}/libavutil/fifo.h \
        ${STAGING_INCDIR}/libavutil/fifo.h
    install -m 0644 ${S}/libavutil/intfloat_readwrite.h \
        ${STAGING_INCDIR}/libavutil/intfloat_readwrite.h
    install -m 0644 ${S}/libavutil/log.h \
        ${STAGING_INCDIR}/libavutil/log.h
    install -m 0644 ${S}/libavutil/lzo.h \
        ${STAGING_INCDIR}/libavutil/lzo.h
    install -m 0644 ${S}/libavutil/mathematics.h \
        ${STAGING_INCDIR}/libavutil/mathematics.h
    install -m 0644 ${S}/libavutil/md5.h \
        ${STAGING_INCDIR}/libavutil/md5.h
    install -m 0644 ${S}/libavutil/mem.h \
        ${STAGING_INCDIR}/libavutil/mem.h
    install -m 0644 ${S}/libavutil/pixfmt.h \
        ${STAGING_INCDIR}/libavutil/pixfmt.h
#    install -m 0644 ${S}/libavutil/random.h \
#        ${STAGING_INCDIR}/libavutil/random.h
    install -m 0644 ${S}/libavutil/rational.h \
        ${STAGING_INCDIR}/libavutil/rational.h
    install -m 0644 ${S}/libavutil/sha1.h \
        ${STAGING_INCDIR}/libavutil/sha1.h

    install -d ${STAGING_INCDIR}/libswscale
    install -m 0644 ${S}/libswscale/swscale.h \
        ${STAGING_INCDIR}/libswscale/swscale.h
}

PACKAGES += "libavcodec libavcodec-dev \
        libavformat libavformat-dev \
        libavutil libavutil-dev \
        libswscale libswscale-dev"

FILES_${PN} = "${bindir}"
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-doc = "${mandir}"

FILES_libavcodec = "${libdir}/libavcodec*.so.*"
FILES_libavcodec-dev = "${libdir}/libavcodec*.so \
        ${libdir}/libavcodec*.la ${libdir}/libavcodec*.a"

FILES_libavformat = "${libdir}/libavformat*.so.*"
FILES_libavformat-dev = "${libdir}/libavformat*.so \
        ${libdir}/libavformat*.la ${libdir}/libavformat*.a"

FILES_libavutil = "${libdir}/libavutil*.so.*"
FILES_libavutil-dev = "${libdir}/libavutil*.so \
        ${libdir}/libavutil*.la ${libdir}/libavutil*.a"

FILES_libswscale = "${libdir}/libswscale*.so.*"
FILES_libswscale-dev = "${libdir}/libswscale*.so \
        ${libdir}/libswscale*.la ${libdir}/libswscale*.a"

