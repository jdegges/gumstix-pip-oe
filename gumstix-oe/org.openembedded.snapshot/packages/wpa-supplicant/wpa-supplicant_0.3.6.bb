DESCRIPTION = "A Client for Wi-Fi Protected Access (WPA)."
SECTION = "network"
LICENSE = "GPL"
HOMEPAGE = "http://hostap.epitest.fi/wpa_supplicant/"
DEPENDS = "openssl"
PR = "r3"

SRC_URI = "http://hostap.epitest.fi/releases/wpa_supplicant-0.3.6.tar.gz \
	file://defconfig \
        file://driver-hermes.patch;patch=1 \
	file://wpa_supplicant.conf"
S = "${WORKDIR}/wpa_supplicant-${PV}"

do_configure () {
	install -m 0755 ${WORKDIR}/defconfig  .config
}

do_compile () {
	make
}

do_install () {
	install -d ${D}${sbindir}
	install -m755 wpa_supplicant ${D}${sbindir}
	install -m755 wpa_passphrase ${D}${sbindir}
	install -m755 wpa_cli        ${D}${sbindir}

	install -d ${D}${sysconfdir}
	install -m644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}

	install -d ${D}${docdir}/wpa_supplicant
	install -m644 README ${D}${docdir}/wpa_supplicant
}
