DESCRIPTION = "UISP is a tool for AVR (and AT89S) microcontrollers which can interface to many hardware in-system programmer"
DEPENDS = "robostix-module"
SECTION = "libs"
LICENSE = "GPL"

PR="r2"

SRC_URI = " \
           http://savannah.nongnu.org/download/uisp/uisp-${PV}.tar.gz \
           file://uisp-robostix.patch;patch=1 \
          "

S = "${WORKDIR}/uisp-${PV}"

inherit autotools

PACKAGES = "${PN}"

do_configure() {
  ac_cv_func_malloc_0_nonnull=yes
	autotools_do_configure
}


