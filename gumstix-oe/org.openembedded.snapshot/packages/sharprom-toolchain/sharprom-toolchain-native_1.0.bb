DESCRIPTION = "Precompiled SharpROM toolchain glue package"
LICENSE = "GPL"
SECTION = "base"
PROVIDES_sharprom-compatible = "\
  virtual/arm-linux-gcc-2.95 \
  virtual/arm-linux-libc-for-gcc \
  virtual/arm-linux-binutils \
  virtual/libc \
  virtual/arm-linux-gcc \
  virtual/linux-libc-headers \
  virtual/arm-linux-gcc-initial "
PR = "r1"

# This needs to be extracted to /usr/local/arm :
# SRC_URI = "http://handhelds.org/download/projects/toolchain/archive/cross-2.95.3.tar.bz2"

inherit native

COMPATIBLE_HOST = 'i.86.*-linux'

do_stage() {
	if [ ! -e /usr/local/arm/2.95.3/bin/arm-linux-gcc-2.95 ]
	then
		die "You need to install the Sharp Toolchain to /usr/local/arm and rename the compiler to arm-linux-gcc-2.95"
	fi
	if [ ! -e /usr/local/arm/2.95.3/bin/arm-linux-ld-2.11.2 ]
	then
		die "You need to install the Sharp Toolchain to /usr/local/arm and rename the linker to arm-linux-ld-2.11.2"
	fi
	ln -sf /usr/local/arm/2.95.3/bin/arm-linux-gcc-2.95 ${STAGING_BINDIR}/arm-linux-gcc
	ln -sf /usr/local/arm/2.95.3/bin/arm-linux-ld-2.11.2 ${STAGING_BINDIR}/arm-linux-ld
}

