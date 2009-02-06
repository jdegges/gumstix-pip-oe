# gumstix x11 image + midori

require gumstix-basic-image.bb

PR="r2"

IMAGE_INSTALL += " \
    xserver-kdrive-fbdev \
    ttf-dejavu-sans \
    ttf-dejavu-sans-mono \
    matchbox \
    matchbox-panel-hacks \
    xcursor-transparent-theme \
    rxvt-unicode \
    gpe-terminal \
    matchbox-keyboard \
    gpe-theme-clearlooks \
    xst \
    xhost \
    xrdb \
    gpe-dm \
    gpe-login \
    gpe-session-scripts \
    gpe-icons \
    gpe-confd \
    gpe-autostarter \
    ${@base_contains("MACHINE_FEATURES", "touchscreen", "libgtkstylus", "",d)} \
    ${@base_contains("MACHINE_FEATURES", "keyboard", "", "libgtkinput",d)} \
    teleport \
    xauth \
    gdk-pixbuf-loader-png \
    gdk-pixbuf-loader-xpm \
    gdk-pixbuf-loader-jpeg \
    pango-module-basic-x \
    pango-module-basic-fc \
    matchbox-panel-manager \
    gpe-su \
    gpe-conf \
    gpe-package \
    gpe-taskmanager \
    keylaunch \
    minilite \
    minimix \
    xmonobut \
    "

#ROOTFS_POSTPROCESS_COMMAND += '${@base_conditional("DISTRO_TYPE", "release", "zap_root_password; ", "",d)}'

