LICENSE = "GPLv2"
HOMEPAGE = "http://geda.seul.org"
PR = "r1"
FILES_${PN} += "${datadir}/gEDA"

DEPENDS = "gtk+ libgeda"

SRC_URI = "http://www.geda.seul.org/devel/${PV}/${P}.tar.gz"

inherit autotools pkgconfig

