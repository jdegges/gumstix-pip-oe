# basic gumstix image customized for pip

require ${GUMSTIXTOP}/com.gumstix.collection/packages/images/gumstix-minimal-image.bb

IMAGE_INSTALL += " \
    cron \
    ntp \
    ntpdate \
    motd \
    mtd-utils \
    libxml2 \
    lighttpd \
    lighttpd-module-access \
    lighttpd-module-accesslog \
    lighttpd-module-cgi \
    lighttpd-module-dirlisting \
    lighttpd-module-indexfile \
    lighttpd-module-staticfile \
    php \
    "


