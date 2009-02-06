require pvrusb2-mci.inc

PR = "r1"
# It in fact requires these modules, but for now is using the local ones.
# RRECOMMEND = "kernel-module-tveeprom kernel-module-tuner kernel-module-msp3400 kernel-module-saa7115"
RRECOMMEND = "kernel-module-tda9887"

SRC_URI = "http://www.isely.net/downloads/pvrusb2-mci-20060101.tar.bz2"

S = "${WORKDIR}/pvrusb2-mci-20060101"

inherit module

CFLAGS = "'-I${KERNEL_SOURCE}/include' \
          '-D__LINUX_ARM_ARCH__=5'"

EXTRA_OEMAKE = "'CFLAGS=${CFLAGS}' \
                'CC=${KERNEL_CC}' \
                'LD=${KERNEL_LD}' \
                'KDIR=${STAGING_KERNEL_DIR}'"

export TARGET_LDFLAGS = "-L${STAGING_DIR}/${TARGET_SYS}/lib \
                         -Wl,-rpath-link,${STAGING_DIR}/${TARGET_SYS}/lib"


do_compile() {
	cd ivtv; oe_runmake
	cd ../driver; oe_runmake
}

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/usb/media
        install -m 0644 ivtv/*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/usb/media
        install -m 0644 driver/*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/usb/media
}
