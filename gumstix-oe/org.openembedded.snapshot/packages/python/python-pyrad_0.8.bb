DESCRIPTION = "Python Radius Client and Server"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD"
SRCNAME = "pyrad"

SRC_URI = "http://www.wiggy.net/files/${SRCNAME}-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils
