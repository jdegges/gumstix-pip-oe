DESCRIPTION = "Hack&Dev's Linux kernel for Palm devices."
HOMEPAGE = "http://www.hackndev.com/"
SECTION = "kernel"
LICENSE = "GPL"
PR = "r11"

COMPATIBLE_MACHINE = "(palmld|palmtc|palmtt3|palmtt5|palmtx|palmz31|palmz72|palmt650)"

SRC_URI = "svn://hackndev.svn.sourceforge.net/svnroot/hackndev/linux4palm/linux;module=trunk;proto=https \
	   file://defconfig"

SRC_URI_append_palmtx += "http://www.busybox.net/downloads/busybox-1.1.0.tar.bz2 \
			  file://init \
                          file://initramfs_list \
                          file://busybox-config"

S = "${WORKDIR}/trunk"

K_MAJOR = "2"
K_MINOR = "6"
K_MICRO = "21"
HHV="hnd2"
PV = "${K_MAJOR}.${K_MINOR}.${K_MICRO}-${HHV}"

inherit kernel

do_configure() {

	if [ -f ${WORKDIR}/defconfig ]; then
		install -m 0644 ${WORKDIR}/defconfig ${S}/.config || die "No configuration for ${MACHINE} available."
	else
		install -m 0644 ${S}/arch/arm/configs/${MACHINE}_defconfig ${S}/.config \
			|| die "No default configuration for ${MACHINE} available."
	fi

	if [ "${TARGET_OS}" == "linux-gnueabi" -o  "${TARGET_OS}" == "linux-uclibcgnueabi" ]; then
                echo "CONFIG_AEABI=y"                   >> ${S}/.config
                echo "CONFIG_OABI_COMPAT=y"             >> ${S}/.config
        else
                echo "# CONFIG_AEABI is not set"        >> ${S}/.config
                echo "# CONFIG_OABI_COMPAT is not set"  >> ${S}/.config
        fi

	if [ -f ${WORKDIR}/initramfs_list ]; then
		install -m 0644 ${WORKDIR}/busybox-config ${WORKDIR}/busybox-1.1.0/.config \
                        || die "No busybox configuration available."

	        echo "CROSS_COMPILER_PREFIX=\"${TARGET_PREFIX}\"" >> ${WORKDIR}/busybox-1.1.0/.config
        	echo "USING_CROSS_COMPILER=y" >> ${WORKDIR}/busybox-1.1.0/.config

                MY_UID=`id -u`
                MY_GID=`id -g`

		echo "CONFIG_INITRAMFS_SOURCE=\"../initramfs_list\"" >> ${S}/.config
		echo "CONFIG_INITRAMFS_ROOT_UID=${MY_UID}" >> ${S}/.config
		echo "CONFIG_INITRAMFS_ROOT_GID=${MY_GID}" >> ${S}/.config
	else
                echo "CONFIG_INITRAMFS_SOURCE=\"\"" >> ${S}/.config
                echo "CONFIG_INITRAMFS_ROOT_UID=0" >> ${S}/.config
                echo "CONFIG_INITRAMFS_ROOT_GID=0" >> ${S}/.config

	fi

        yes '' | oe_runmake oldconfig

}


do_compile_prepend() {
	if [ -f ${WORKDIR}/initramfs_list ]; then
                cd ${WORKDIR}/busybox-1.1.0
                oe_runmake 'CC=${CC}' 'CROSS=${HOST_PREFIX}'
                cd -
	fi
}

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0644 arch/${ARCH}/boot/${KERNEL_IMAGETYPE} ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${PV}-${MACHINE}-${DATETIME}
}   

do_deploy[dirs] = "${S}"

addtask deploy before do_build after do_compile
