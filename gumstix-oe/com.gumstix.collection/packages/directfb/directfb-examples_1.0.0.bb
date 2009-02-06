DESCRIPTION = "DirectFB extra providers"
DEPENDS = "directfb"
SECTION = "libs"
LICENSE = "GPL"

PR="r1"

SRC_URI = " \
           http://www.directfb.org/downloads/Extras/DirectFB-examples-${PV}.tar.gz \
          "
S = "${WORKDIR}/DirectFB-examples-${PV}"

CFLAGS += "-I/${STAGING_INCDIR}/directfb"

inherit autotools

do_configure_append() {
#    find ${S} -type f | xargs sed -i 's:/usr/lib:${STAGING_LIBDIR}:'
    find ${S} -type f | xargs sed -i 's:/usr/include:${STAGING_INCDIR}:'
}

do_stage() {
	autotools_stage_all
}


