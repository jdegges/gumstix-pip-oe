LICENSE = "LGPL"
DESCRIPTION = "GTK+ is a multi-platform toolkit for creating graphical user interfaces. Offering a complete \
set of widgets, GTK+ is suitable for projects ranging from small one-off projects to complete application suites."
HOMEPAGE = "http://www.gtk.org"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS = "glib-2.0 pango atk jpeg libpng libxext libxcursor gtk-doc libgcrypt cairo"

inherit autotools pkgconfig

FILES_${PN} = "${bindir}/gdk-pixbuf-query-loaders \
	${bindir}/gtk-update-icon-cache \
	${bindir}/gtk-query-immodules-2.0 \
	${libdir}/lib*.so.* \
	${datadir}/themes ${sysconfdir} \
	${libdir}/gtk-2.0/${LIBV}/engines/libpixmap.so \
"
FILES_${PN}-dev += " \
        ${datadir}/gtk-2.0/include \
        ${libdir}/gtk-2.0/include \
        ${libdir}/gtk-2.0/${LIBV}/loaders/*.la \
        ${libdir}/gtk-2.0/${LIBV}/immodules/*.la \
        ${libdir}/gtk-2.0/${LIBV}/engines/*.la \
	${libdir}/gtk-2.0/${LIBV}/printbackends/*.la \
        ${bindir}/gdk-pixbuf-csource"
FILES_${PN}-dbg += " \
        ${libdir}/gtk-2.0/${LIBV}/loaders/.debug/* \
        ${libdir}/gtk-2.0/${LIBV}/immodules/.debug/* \
        ${libdir}/gtk-2.0/${LIBV}/engines/.debug/* \
	${libdir}/gtk-2.0/${LIBV}/printbackends/.debug/*"


NEATSTUFF = " ttf-dejavu-sans gdk-pixbuf-loader-png gdk-pixbuf-loader-jpeg gdk-pixbuf-loader-gif gdk-pixbuf-loader-xpm "

RRECOMMENDS_${PN} = " ${NEATSTUFF} "
RRECOMMENDS_${PN}_linux = " ${NEATSTUFF} glibc-gconv-iso8859-1 "
RRECOMMENDS_${PN}_linux-gnueabi = " ${NEATSTUFF} glibc-gconv-iso8859-1"

EXTRA_OECONF = "--without-libtiff --disable-xkb --disable-glibtest --enable-display-migration --without-cups"

export GDKTARGET="x11"

LIBV = "2.10.0"

do_stage () {
	oe_libinstall -so -C gtk libgtk-$GDKTARGET-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C gdk libgdk-$GDKTARGET-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C contrib/gdk-pixbuf-xlib libgdk_pixbuf_xlib-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C gdk-pixbuf libgdk_pixbuf-2.0 ${STAGING_LIBDIR}

	autotools_stage_includes

	mkdir -p ${STAGING_LIBDIR}/gtk-2.0/include
	install -m 0644 gdk/gdkconfig.h ${STAGING_LIBDIR}/gtk-2.0/include/gdkconfig.h
        # Copy over all headers, since the maemo stuff needs access to the private api. *sigh*
	cp gtk/*.h ${STAGING_INCDIR}/gtk-2.0/gtk/
	install -m 0644 m4macros/gtk-2.0.m4 ${STAGING_DATADIR}/aclocal/
}

do_install_append () {
	install -d ${D}${sysconfdir}/gtk-2.0
}

postinst_prologue() {
if [ "x$D" != "x" ]; then
  exit 1
fi

}

PACKAGES_DYNAMIC = "gdk-pixbuf-loader-* gtk-immodule-* gtk-printbackend-*"

python populate_packages_prepend () {
	import os.path

	prologue = bb.data.getVar("postinst_prologue", d, 1)

	gtk_libdir = bb.data.expand('${libdir}/gtk-2.0/${LIBV}', d)
	loaders_root = os.path.join(gtk_libdir, 'loaders')
	immodules_root = os.path.join(gtk_libdir, 'immodules')
	printmodules_root = os.path.join(gtk_libdir, 'printbackends');

	do_split_packages(d, loaders_root, '^libpixbufloader-(.*)\.so$', 'gdk-pixbuf-loader-%s', 'GDK pixbuf loader for %s', prologue + 'gdk-pixbuf-query-loaders > /etc/gtk-2.0/gdk-pixbuf.loaders')
	do_split_packages(d, immodules_root, '^im-(.*)\.so$', 'gtk-immodule-%s', 'GTK input module for %s', prologue + 'gtk-query-immodules-2.0 > /etc/gtk-2.0/gtk.immodules')
	do_split_packages(d, printmodules_root, '^libprintbackend-(.*)\.so$', 'gtk-printbackend-%s', 'GTK printbackend module for %s')

        if (bb.data.getVar('DEBIAN_NAMES', d, 1)):
                bb.data.setVar('PKG_${PN}', 'libgtk-2.0', d)
}
