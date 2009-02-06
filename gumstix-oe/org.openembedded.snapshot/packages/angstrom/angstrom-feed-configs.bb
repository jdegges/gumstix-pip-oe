DESCRIPTION = "Configuration files for online package repositories aka feeds"

PR = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

FEED_BASEPATH ?= "unstable/feed/"

do_compile() {
        mkdir -p ${S}/${sysconfdir}/ipkg
	for feed in base debug perl python gstreamer ; do
          echo "src/gz ${feed} ${ANGSTROM_URI}/${FEED_BASEPATH}${FEED_ARCH}/${feed}" > ${S}/${sysconfdir}/ipkg/${feed}-feed.conf
	done

        echo "src/gz ${MACHINE_ARCH} ${ANGSTROM_URI}/${FEED_BASEPATH}${FEED_ARCH}/${MACHINE_ARCH}" >  ${S}/${sysconfdir}/ipkg/${MACHINE_ARCH}-feed.conf
	echo "src/gz no-arch ${ANGSTROM_URI}/${FEED_BASEPATH}/all" > ${S}/${sysconfdir}/ipkg/noarch-feed.conf
}


do_install () {
        install -d ${D}${sysconfdir}/ipkg
	install -m 0644  ${S}/${sysconfdir}/ipkg/* ${D}${sysconfdir}/ipkg/
}

CONFFILES_${PN} += "${sysconfdir}/ipkg/base-feed.conf \
                    ${sysconfdir}/ipkg/debug-feed.conf \
                    ${sysconfdir}/ipkg/perl-feed.conf \
                    ${sysconfdir}/ipkg/python-feed.conf \
                    ${sysconfdir}/ipkg/gstreamer-feed.conf \
                    ${sysconfdir}/ipkg/${MACHINE_ARCH}-feed.conf \
                    ${sysconfdir}/ipkg/noarch-feed.conf \
                   "

