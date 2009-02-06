#Angstrom X11 image, with apps and kernel modules included

ANGSTROM_EXTRA_INSTALL += " \
                           ${@base_contains("MACHINE_FEATURES", "phone", "openmoko-dialer2", "",d)} \
			  " 
XSERVER ?= "xserver-kdrive-fbdev"

export IMAGE_BASENAME = "x11-office-image"

DEPENDS = "task-base"
IMAGE_INSTALL = "\
    ${XSERVER} \
    task-base-extended \
    angstrom-x11-base-depends \
    angstrom-gpe-task-base \
    angstrom-gpe-task-settings \
    kernel-modules \
    hal \
    abiword \
    gnumeric \
    cups \
    claws-mail claws-mail claws-plugin-mailmbox claws-plugin-rssyl \
    midori minimo \
    gphoto2 \
    ${ANGSTROM_EXTRA_INSTALL}"

#zap root password for release images
ROOTFS_POSTPROCESS_COMMAND += '${@base_conditional("DISTRO_TYPE", "release", "zap_root_password; ", "",d)}'

inherit image
