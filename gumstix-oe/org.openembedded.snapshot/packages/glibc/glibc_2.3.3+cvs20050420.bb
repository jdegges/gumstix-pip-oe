require glibc.inc

DEFAULT_PREFERENCE_i586 = "0"

DEFAULT_PREFERENCE_sh3 = "-99"

FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/glibc-cvs"
PR = "r6"

GLIBC_ADDONS ?= "linuxthreads"

DEFAULT_PREFERENCE = "-1"

#	   file://noinfo.patch;patch=1
#	   file://ldconfig.patch;patch=1;pnum=0
SRC_URI = "cvs://anoncvs@sources.redhat.com/cvs/glibc;module=libc;date=${@bb.data.getVar('PV', d, 1)[9:]} \
	   file://arm-ioperm.patch;patch=1;pnum=0 \
	   file://fhs-linux-paths.patch;patch=1 \
	   file://arm-no-hwcap.patch;patch=1;pnum=0 \
	   file://arm-memcpy.patch;patch=1;pnum=0 \
	   file://arm-longlong.patch;patch=1;pnum=0 \
	   file://arm-machine-gmon.patch;patch=1;pnum=0 \
	   file://trampoline.patch;patch=1;pnum=0 \
	   file://eabi-patch-1;patch=1 \
	   file://eabi-patch-2;patch=1 \
	   file://eabi-patch-3;patch=1 \
	   file://5090_all_stubs-rule-fix.patch;patch=1 \
           file://etc/ld.so.conf \
	   file://generate-supported.mk"

# seems to fail on tls platforms
SRC_URI_append_arm = " file://dyn-ldconfig-20041128.patch;patch=1"

SRC_URI_append_openmn = " file://ldsocache-varrun.patch;patch=1"

S = "${WORKDIR}/libc"
B = "${WORKDIR}/build-${TARGET_SYS}"

EXTRA_OECONF = "--enable-kernel=${OLDEST_KERNEL} \
	        --without-cvs --disable-profile --disable-debug --without-gd \
		--enable-clocale=gnu \
	        --enable-add-ons=${GLIBC_ADDONS} \
		--with-headers=${CROSS_DIR}/${TARGET_SYS}/include \
		--without-selinux \
		${GLIBC_EXTRA_OECONF}"

do_configure () {
# override this function to avoid the autoconf/automake/aclocal/autoheader
# calls for now
# don't pass CPPFLAGS into configure, since it upsets the kernel-headers
# version check and doesn't really help with anything
	if [ -z "`which rpcgen`" ]; then
		echo "rpcgen not found.  Install glibc-devel."
		exit 1
	fi
	(cd ${S} && gnu-configize) || die "failure in running gnu-configize"
	CPPFLAGS="" oe_runconf
}

rpcsvc = "bootparam_prot.x nlm_prot.x rstat.x \
	  yppasswd.x klm_prot.x rex.x sm_inter.x mount.x \
	  rusers.x spray.x nfs_prot.x rquota.x key_prot.x"

do_compile () {
	# this really is arm specific
	touch ${S}/sysdeps/arm/framestate.c
	# -Wl,-rpath-link <staging>/lib in LDFLAGS can cause breakage if another glibc is in staging
	unset LDFLAGS
	base_do_compile
	(
		cd ${S}/sunrpc/rpcsvc
		for r in ${rpcsvc}; do
			h=`echo $r|sed -e's,\.x$,.h,'`
			rpcgen -h $r -o $h || oewarn "unable to generate header for $r"
		done
	)
}

do_stage() {
	rm -f ${STAGING_LIBDIR}/libc.so.6
	oe_runmake 'install_root=${STAGING_DIR}/${HOST_SYS}' \
		   'includedir=/include' 'libdir=/lib' 'slibdir=/lib' \
		   '${STAGING_LIBDIR}/libc.so.6' \
		   install-headers install-lib

	install -d ${STAGING_INCDIR}/gnu \
		   ${STAGING_INCDIR}/bits \
		   ${STAGING_INCDIR}/rpcsvc
	install -m 0644 ${S}/include/gnu/stubs.h ${STAGING_INCDIR}/gnu/
	install -m 0644 ${B}/bits/stdio_lim.h ${STAGING_INCDIR}/bits/
	install -m 0644 misc/syscall-list.h ${STAGING_INCDIR}/bits/syscall.h
	for r in ${rpcsvc}; do
		h=`echo $r|sed -e's,\.x$,.h,'`
		install -m 0644 ${S}/sunrpc/rpcsvc/$h ${STAGING_INCDIR}/rpcsvc/
	done
	for i in libc.a libc_pic.a libc_nonshared.a; do
		install -m 0644 ${B}/$i ${STAGING_LIBDIR}/ || die "failed to install $i"
	done
	echo 'GROUP ( libpthread.so.0 libpthread_nonshared.a )' > ${STAGING_LIBDIR}/libpthread.so
	echo 'GROUP ( libc.so.6 libc_nonshared.a )' > ${STAGING_LIBDIR}/libc.so
}

require glibc-package.bbclass
