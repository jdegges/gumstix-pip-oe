LICENSE = "LGPL"
DESCRIPTION = "GTK+ is a multi-platform toolkit for creating graphical user interfaces. Offering a complete \
set of widgets, GTK+ is suitable for projects ranging from small one-off projects to complete application suites."
HOMEPAGE = "http://www.gtk.org"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS = "glib-2.0 pango atk jpeg libpng libxext libxcursor"
PR = "r7"

SRC_URI = "ftp://ftp.gtk.org/pub/gtk/v2.4/gtk+-${PV}.tar.bz2 \
           file://no-demos.patch;patch=1 \
           file://no-xwc.patch;patch=1;pnum=0 \
           file://automake-lossage.patch;patch=1 \
           file://gtk+-handhelds.patch;patch=1 \
           file://hardcoded_libtool.patch;patch=1 \
	   file://spinbutton.patch;patch=1 \
	   file://disable-tooltips.patch;patch=1 \
	   file://gtklabel-resize-patch;patch=1 \
	   file://menu-deactivate.patch;patch=1 \
	   file://xsettings.patch;patch=1 \
	   file://scroll-timings.patch;patch=1 \
	   file://gtk-doc.m4 \
	   file://small-gtkfilesel.patch;patch=1 \
	   file://single-click.patch;patch=1"

inherit autotools pkgconfig flow-lossage

do_configure_prepend() {
	install -d m4
	install ${WORKDIR}/gtk-doc.m4 m4/
}

FILES_${PN} = "${bindir}/gdk-pixbuf-query-loaders \
	${bindir}/gtk-query-immodules-2.0 \
	${libdir}/lib*.so.* \
	${datadir}/themes ${sysconfdir}"
FILES_${PN}-dev += "${datadir}/gtk-2.0/include ${libdir}/gtk-2.0/include ${bindir}/gdk-pixbuf-csource"

RRECOMMENDS_${PN} = "glibc-gconv-iso8859-1 ttf-dejavu-sans"

EXTRA_OECONF = "--without-libtiff --disable-xkb"
# --disable-cruft

LIBV = "2.4.0"

do_stage () {
	oe_libinstall -so -C gtk libgtk-x11-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C gdk libgdk-x11-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C contrib/gdk-pixbuf-xlib libgdk_pixbuf_xlib-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C gdk-pixbuf libgdk_pixbuf-2.0 ${STAGING_LIBDIR}

	autotools_stage_includes

	mkdir -p ${STAGING_LIBDIR}/gtk-2.0/include
	install -m 0644 gdk/gdkconfig.h ${STAGING_LIBDIR}/gtk-2.0/include/gdkconfig.h

	install -m 0644 m4macros/gtk-2.0.m4 ${STAGING_DATADIR}/aclocal/
}

do_install_append () {
	install -d ${D}${sysconfdir}/gtk-2.0
}

PACKAGES_DYNAMIC = "gdk-pixbuf-loader-* gtk-immodule-*"

python populate_packages_prepend () {
	import os.path

	gtk_libdir = bb.data.expand('${libdir}/gtk-2.0/${LIBV}', d)
	loaders_root = os.path.join(gtk_libdir, 'loaders')
	immodules_root = os.path.join(gtk_libdir, 'immodules')

	do_split_packages(d, loaders_root, '^libpixbufloader-(.*)\.so$', 'gdk-pixbuf-loader-%s', 'GDK pixbuf loader for %s', 'gdk-pixbuf-query-loaders > /etc/gtk-2.0/gdk-pixbuf.loaders')
	do_split_packages(d, immodules_root, '^im-(.*)\.so$', 'gtk-immodule-%s', 'GTK input module for %s', 'gtk-query-immodules > /etc/gtk-2.0/gtk.immodules')

        if (bb.data.getVar('DEBIAN_NAMES', d, 1)):
                bb.data.setVar('PKG_${PN}', 'libgtk-2.0', d)
}
