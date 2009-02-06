DESCRIPTION = "The Enlightened Widget Library, \
a simple-to-use general purpose widget library \
based on the enlightenment foundation libraries."
DEPENDS = "evas ecore edje emotion efreet"
RSUGGESTS_${PN} += "ewl-themes"
LICENSE = "MIT"
PV = "0.5.1+cvs${SRCDATE}"
PR = "r0"

inherit efl_library

# TODO package engines more granular
PACKAGES += "${PN}-plugins ${PN}-engines"

FILES_${PN} += "${sysconfdir}/ewl/*"
FILES_${PN}-tests += "${libdir}/ewl/tests/*.so*"
FILES_${PN}-dev += "${libdir}/ewl/*/*.a ${libdir}/ewl/*/*.la"
FILES_${PN}-dbg += "${libdir}/ewl/*/.debug"

FILES_${PN}-engines = "${libdir}/ewl/engines/*.so*"
FILES_${PN}-plugins = "${libdir}/ewl/plugins/*.so*"
