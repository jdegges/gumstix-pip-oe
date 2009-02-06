DESCRIPTION = "Scapy is a powerful interactive packet manipulation tool, \
packet generator, network scanner, network discovery, packet sniffer, etc. \
It can for the moment replace hping, 85% of nmap, arpspoof, arp-sk, arping, \
tcpdump, tethereal, p0f, ...."
SECTION = "devel/python"
HOMEPAGE = "http://www.secdev.org/projects/scapy/"
LICENSE = "GPL"
PRIORITY = "optional"
RDEPENDS = "python-netclient python-netserver"
SRCNAME = "scapy"

SRC_URI = "http://www.secdev.org/projects/scapy/files/scapy-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

do_install() {
	install -d ${D}${libdir}/${PYTHON_DIR}
	install -m 0755 ${S}/scapy.py ${D}${libdir}/${PYTHON_DIR}/
}

FILES_${PN} = "${libdir}/${PYTHON_DIR}/"
