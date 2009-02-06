DESCRIPTION = "Access NOAA's METAR weather reports"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "GPL"
RDEPENDS = "python-core"
SRCNAME = "pymetar"

SRC_URI = "http://www.schwarzvogel.de/pkgs/${SRCNAME}-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils
