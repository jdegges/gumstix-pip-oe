DESCRIPTION = "Linux Kernel for the EFIKA dev platform"
SECTION = "kernel"
LICENSE = "GPL"
PR = "r3"

COMPATIBLE_MACHINE = "efika"

SRC_URI = "http://www.efika.de/download/linux-2.6.19-rc6_efika.tgz \
           file://0001-sound-Add-support-for-the-MPC52xx-PSC-AC97-Link.txt;patch=1 \
           file://0001-powerpc-Add-device-tree-fixup-for-the-EFIKA.txt;patch=1 \
           file://defconfig \
		   "
#	http://www.246tnt.com/files/0001-sound-Add-support-for-the-MPC52xx-PSC-AC97-Link.txt;patch=1 \
#           http://lkml.org/lkml/2006/11/29/335;patch=1 \

S = "${WORKDIR}/linux-2.6.19-rc6_efika"

inherit kernel

export ARCH="powerpc"


do_configure() {
		install -m 644 ${WORKDIR}/defconfig ${S}/.config
		yes | make ARCH=${ARCH} oldconfig
}

do_deploy() {
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 0644 arch/${ARCH}/boot/${KERNEL_IMAGETYPE} ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${PV}-${MACHINE}-${DATETIME}
}

do_deploy[dirs] = "${S}"

addtask deploy before do_build after do_compile


