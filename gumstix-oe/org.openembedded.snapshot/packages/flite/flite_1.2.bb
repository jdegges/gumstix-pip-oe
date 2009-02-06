DESCRIPTION = "flite - the festival light speech synthesizer"
PRIORITY = "optional"
HOMEPAGE = "http://fife.speech.cs.cmu.edu/flite/"
SECTION = "console/utils"
LICENSE = "flite"
PR = "r2"

PARALLEL_MAKE = ""

SRC_URI = "http://www.speech.cs.cmu.edu/flite/packed/flite-${PV}/flite-${PV}-release.tar.bz2 \
           file://configure-with-audio.patch;patch=1 \
           file://fix-read-only-assignments.patch;patch=1"
S = "${WORKDIR}/flite-${PV}-release"

inherit autotools

EXTRA_OECONF = "--with-audio=oss --enable-shared"

PACKAGES += "lib${PN} lib${PN}-vox8 lib${PN}-vox16"

FILES_${PN} = "${bindir}"

FILES_lib${PN} ="${libdir}/libflite.so.* \
                 ${libdir}/libflite_cmu_time_awb.so.* \
		 ${libdir}/libflite_cmulex.so.* \
		 ${libdir}/libflite_usenglish.so.*"

FILES_lib${PN}-vox8 = "${libdir}/libflite_cmu_us_kal.so.*"

FILES_lib${PN}-vox16 = "${libdir}/libflite_cmu_us_kal16.so.*"

LEAD_SONAME = "libflite.so"

do_install() {
        oe_runmake INSTALLBINDIR="${D}${bindir}" INSTALLLIBDIR="${D}${libdir}" INSTALLINCDIR="${D}${includedir}" install
}


do_stage () {
        install -m 0644 ${S}/include/*.h ${STAGING_INCDIR}/
        oe_libinstall -C lib -a -so libflite ${STAGING_LIBDIR}
        oe_libinstall -C lib -a -so libflite_cmu_time_awb ${STAGING_LIBDIR}
        oe_libinstall -C lib -a -so libflite_cmulex ${STAGING_LIBDIR}
        oe_libinstall -C lib -a -so libflite_usenglish ${STAGING_LIBDIR}
        oe_libinstall -C lib -a -so libflite_cmu_us_kal ${STAGING_LIBDIR}
        oe_libinstall -C lib -a -so libflite_cmu_us_kal16 ${STAGING_LIBDIR}
}
