DESCRIPTION = "Image Analyzer"
HOMEPAGE = "http://github.com/jdegges/image-analyzer/tree/master"
LICENSE = "MIT"
PV = "dev"
PR = "r0"

DEPENDS = "FreeImage ffmpeg "

SRC_URI = "git://github.com/jdegges/image-analyzer.git;protocol=git;tag=master "

S = "${WORKDIR}/git/"

inherit autotools

FILES_${PN} = "${bindir}/ia"
