SECTION = "kernel"
DESCRIPTION = "Linux kernel for the Mainstone (PXA270 ref design)"
LICENSE = "GPL"
PR = "r0"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.18.tar.bz2 \
           file://mainstone_defconfig"

S = "${WORKDIR}/linux-2.6.18"

COMPATIBLE_HOST = 'arm.*-linux'

inherit kernel
inherit package

ARCH = "arm"
#CMDLINE_CONSOLE ?= "ttyS0,115200n8"
#CMDLINE_ROOT = "root=/dev/slug rootfstype=ext2,jffs2 initrd=0x01000000,10M mem=32M@0x00000000"
#CMDLINE_ROOT = "root=/dev/mtdblock4 rootfstype=jffs2 mem=32M@0x00000000"
#CMDLINE_ROOT = "root=/dev/ram0 rw rootfstype=ext2,jffs2 initrd=0x01000000,10M init=/linuxrc mem=32M@0x00000000"
#CMDLINE = "${CMDLINE_ROOT} ${CMDLINE_CONSOLE}"

do_configure_prepend() {
	install -m 0644 ${WORKDIR}/${MACHINE}_defconfig ${S}/.config
#	echo "CONFIG_CMDLINE=\"${CMDLINE}\"" >> ${S}/.config
}

do_deploy() {
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 0644 arch/${ARCH}/boot/${KERNEL_IMAGETYPE} ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}-${DATETIME}.bin
}

do_deploy[dirs] = "${S}"

addtask deploy before do_build after do_compile

COMPATIBLE_MACHINE = "mainstone"
