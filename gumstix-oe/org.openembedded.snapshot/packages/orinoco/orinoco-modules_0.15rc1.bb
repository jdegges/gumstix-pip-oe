DESCRIPTION = "A driver for wireless LAN cards based on Hermes(Orinoco) cards. \
Also contains support for cards using downloadable firmware, i.e. the Symbol/Socket family."
SECTION = "kernel/modules"
PRIORITY = "optional"
DEPENDS = "orinoco-conf spectrum-fw"
RDEPENDS = "orinoco-conf"
LICENSE = "GPL"
PR = "r7"

SRC_URI = "${SOURCEFORGE_MIRROR}/orinoco/orinoco-${PV}.tar.gz \
           file://makefile_fix.patch;patch=1 \
           file://list-move.patch;patch=1 \
           file://add_event.patch;patch=1 \
           file://add_utsname.patch;patch=1 \
           file://spectrum_cs_ids.patch;patch=1"
S = "${WORKDIR}/orinoco-${PV}"

inherit module

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/net/
        install -m 0644 *${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/net/
}

PACKAGES = "orinoco-modules-cs orinoco-modules-pci orinoco-modules-usb orinoco-modules-nortel orinoco-modules"
FILES_orinoco-modules-cs = "/lib/modules/${KERNEL_VERSION}/net/*_cs${KERNEL_OBJECT_SUFFIX}"
FILES_orinoco-modules-pci = "/lib/modules/${KERNEL_VERSION}/net/orinoco_p*${KERNEL_OBJECT_SUFFIX}"
FILES_orinoco-modules-usb = "/lib/modules/${KERNEL_VERSION}/net/*_usb${KERNEL_OBJECT_SUFFIX}"
FILES_orinoco-modules-nortel = "/lib/modules/${KERNEL_VERSION}/net/orinoco_tmd${KERNEL_OBJECT_SUFFIX} \
                                /lib/modules/${KERNEL_VERSION}/net/orinoco_nortel${KERNEL_OBJECT_SUFFIX}"
FILES_orinoco-modules = "/lib/modules/"
RDEPENDS_orinoco-modules-cs = "orinoco-modules spectrum-fw"
RDEPENDS_orinoco-modules-pci = "orinoco-modules"
RDEPENDS_orinoco-modules-usb = "orinoco-modules"
RDEPENDS_orinoco-modules-nortel = "orinoco-modules"
