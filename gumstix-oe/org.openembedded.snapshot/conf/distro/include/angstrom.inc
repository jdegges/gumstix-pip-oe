#@TYPE: Distribution
#@NAME: Angstrom 
#@DESCRIPTION: Release independent distribution configuration for Angstrom

DISTRO = "angstrom"
DISTRO_NAME = "Angstrom"

TARGET_VENDOR = "-angstrom"

# Can be "glibc", "eglibc" or "uclibc"
ANGSTROM_MODE ?= "glibc"

DEPLOY_DIR = "${TMPDIR}/deploy/${ANGSTROM_MODE}"
require conf/distro/include/angstrom-${ANGSTROM_MODE}.inc

#Use this variable in feeds and other parts that need a URI
ANGSTROM_URI ?= "http://www.angstrom-distribution.org"

#Use this variable to select which recipe you want to use to get feed configs (/etc/ipkg/*, /etc/apt/sources.list)
# usefull for derivative distros and local testing
# As this is runtime we can't and won't use virtual/feed-configs
ANGSTROM_FEED_CONFIGS ?= "angstrom-feed-configs"

#Set the default maintainer to angstrom-dev
MAINTAINER = "Angstrom Developers <angstrom-distro-devel@linuxtogo.org>"

#use debian style naming
#use multimachine buildrules 
INHERIT += "debian multimachine sanity devshell angstrom-mirrors insane"

ANGSTROM_PKG_FORMAT ?= "ipk"
require conf/distro/include/angstrom-package-${ANGSTROM_PKG_FORMAT}.inc

#Generate locales on the buildsystem instead of on the target. Speeds up first boot, set to "1" to enable
PREFERRED_PROVIDER_qemu-native = "qemu-native"
ENABLE_BINARY_LOCALE_GENERATION ?= "1"

#qemu doesn't like armv6/eabi/vfp
ENABLE_BINARY_LOCALE_GENERATION_mx31ads = "0"
ENABLE_BINARY_LOCALE_GENERATION_nokia800 = "0"
ENABLE_BINARY_LOCALE_GENERATION_omap2420h4 = "0"
ENABLE_BINARY_LOCALE_GENERATION_omap2430sdp = "0"

#ARM EABI is softfloat by default, but let's make sure :)
#make it overridable for platforms with FPU, like ep93xx or i.mx31
TARGET_FPU_arm ?= "soft"
TARGET_FPU_armeb ?= "soft"
TARGET_FPU_ixp4xx ?= "soft"

#Set the right arch for the feeds
#Alphabetically sorted

FEED_ARCH ?= "${TARGET_ARCH}"

#blackfin machines
FEED_ARCH_bfin          = "blackfin"

#armv4t machines

FEED_ARCH_acern30 	= "armv4t"
FEED_ARCH_amsdelta 	= "armv4t"
FEED_ARCH_ep93xx 	= "armv4t"
FEED_ARCH_eteng500 	= "armv4t"
FEED_ARCH_fic-gta01 	= "armv4t"
FEED_ARCH_fic-gta02 	= "armv4t"
FEED_ARCH_h1940 	= "armv4t"
FEED_ARCH_h6300 	= "armv4t"
FEED_ARCH_kb9202 	= "armv4t"
FEED_ARCH_ks8695 	= "armv4t"
FEED_ARCH_rx1950 	= "armv4t"
FEED_ARCH_rx3000 	= "armv4t"
FEED_ARCH_sarge-at91 	= "armv4t"
FEED_ARCH_smdk2440 	= "armv4t"
FEED_ARCH_smdk2443 	= "armv4t"

#armv5t machines

FEED_ARCH_a1200 	= "armv5te"
FEED_ARCH_a780 		= "armv5te"
FEED_ARCH_akita 	= "armv5te"
FEED_ARCH_asus620 	= "armv5te"
FEED_ARCH_asus730 	= "armv5te"
FEED_ARCH_at91sam9263ek = "armv5te"
FEED_ARCH_aximx50 	= "armv5te"
FEED_ARCH_aximx50v 	= "armv5te"
FEED_ARCH_c7x0 		= "armv5te"
FEED_ARCH_compulab-pxa270 = "armv5te"
FEED_ARCH_davinci-dvevm = "armv5te"
FEED_ARCH_devkitidp-pxa255 = "armv5te"
FEED_ARCH_e680 		= "armv5te"
FEED_ARCH_er0100 	= "armv5te"
FEED_ARCH_gumstix 	= "armv5te"
FEED_ARCH_gumstix-verdex = "armv5te"
FEED_ARCH_gumstix-connex = "armv5te"
FEED_ARCH_h1910 	= "armv5te"
FEED_ARCH_h2200 	= "armv5te"
FEED_ARCH_h3900 	= "armv5te"
FEED_ARCH_h4000 	= "armv5te"
FEED_ARCH_h5000 	= "armv5te"
FEED_ARCH_htcalpine 	= "armv5te"
FEED_ARCH_htcapache 	= "armv5te"
FEED_ARCH_htcblueangel 	= "armv5te"
FEED_ARCH_htchimalaya 	= "armv5te"
FEED_ARCH_htcsable 	= "armv5te"
FEED_ARCH_htctornado 	= "armv5te"
FEED_ARCH_htcuniversal 	= "armv5te"
FEED_ARCH_hx2000 	= "armv5te"
FEED_ARCH_hx4700 	= "armv5te"
FEED_ARCH_ixp4xxle 	= "armv5te"
FEED_ARCH_logicpd-pxa270 = "armv5te"
FEED_ARCH_looxc550 	= "armv5te"
FEED_ARCH_lsarm 	= "armv5te"
FEED_ARCH_magician 	= "armv5te"
FEED_ARCH_mainstone 	= "armv5te"
FEED_ARCH_mnci 		= "armv5te"
FEED_ARCH_mtx-3 	= "armv5te"
FEED_ARCH_mx21ads 	= "armv5te"
FEED_ARCH_n2100 	= "armv5te"
FEED_ARCH_navman-icn330 = "armv5te"
FEED_ARCH_netbook-pro 	= "armv5te"
FEED_ARCH_nokia770 	= "armv5te"
FEED_ARCH_rokre2 	= "armv5te"
FEED_ARCH_nslu2le 	= "armv5te"
FEED_ARCH_omap1510inn 	= "armv5te"
FEED_ARCH_omap1610h2 	= "armv5te"
FEED_ARCH_omap1710h3 	= "armv5te"
FEED_ARCH_omap5912osk 	= "armv5te"
FEED_ARCH_palmld 	= "armv5te"
FEED_ARCH_palmt650 	= "armv5te"
FEED_ARCH_palmt680 	= "armv5te"
FEED_ARCH_palmtc 	= "armv5te"
FEED_ARCH_palmtt 	= "armv5te"
FEED_ARCH_palmtt3 	= "armv5te"
FEED_ARCH_palmtt5 	= "armv5te"
FEED_ARCH_palmtx 	= "armv5te"
FEED_ARCH_palmz31 	= "armv5te"
FEED_ARCH_palmz71 	= "armv5te"
FEED_ARCH_palmz72 	= "armv5te"
FEED_ARCH_poodle 	= "armv5te"
FEED_ARCH_qemuarm 	= "armv5te"
FEED_ARCH_spitz 	= "armv5te"
FEED_ARCH_tosa 		= "armv5te"
FEED_ARCH_triton 	= "armv5te"

FEED_ARCH_ixp4xxbe 	= "armv5teb"
FEED_ARCH_nslu2be 	= "armv5teb"

# armv6

FEED_ARCH_mx31ads 	= "armv6"
FEED_ARCH_nokia800	= "armv6"  
FEED_ARCH_omap2420h4	= "armv6"
FEED_ARCH_omap2430sdp   = "armv6"

#i486 machines
FEED_ARCH_geodegx 	= "i486"
FEED_ARCH_geodelx 	= "i486"
FEED_ARCH_netvista 	= "i486"
FEED_ARCH_wrap 		= "i486"
FEED_ARCH_x86 		= "i486"

#i586 machines
FEED_ARCH_epia 		= "i586"
FEED_ARCH_i586-generic 	= "i586"
FEED_ARCH_qemux86 	= "i586"

#i686 machines

FEED_ARCH_alix 		= "i686"
FEED_ARCH_colinux	= "i686"
FEED_ARCH_guinness 	= "i686"
FEED_ARCH_i686-generic 	= "i686"
FEED_ARCH_progear 	= "i686"

#powerpc machines

FEED_ARCH_dht-walnut 	= "ppc405"
FEED_ARCH_magicbox 	= "ppc405"
FEED_ARCH_xilinx-ml403 	= "ppc405"
FEED_ARCH_xilinx-ml410 	= "ppc405"
FEED_ARCH_sequoia 	= "ppc440e"
FEED_ARCH_efika 	= "ppc603e"
FEED_ARCH_lite5200 	= "ppc603e"
FEED_ARCH_lsppchd 	= "ppc603e"
FEED_ARCH_lsppchg 	= "ppc603e"
FEED_ARCH_storcenter 	= "ppc603e"
FEED_ARCH_turbostation 	= "ppc603e"


#strongarm machines, no EABI

FEED_ARCH_collie 	= "arm-oabi"
FEED_ARCH_h3600 	= "arm-oabi"
FEED_ARCH_h3800 	= "arm-oabi"
FEED_ARCH_htcwallaby 	= "arm-oabi"
FEED_ARCH_jornada56x 	= "arm-oabi"
FEED_ARCH_jornada7xx 	= "arm-oabi"
FEED_ARCH_shark 	= "arm-oabi"
FEED_ARCH_simpad 	= "arm-oabi"


#Tweak packaging for strongarm machines since they can't use EABI

PACKAGE_EXTRA_ARCHS_collie = "arm-oabi"
PACKAGE_EXTRA_ARCHS_h3600  = "arm-oabi"
PACKAGE_EXTRA_ARCHS_h3800  = "arm-oabi"
PACKAGE_EXTRA_ARCHS_simpad = "arm-oabi"
PACKAGE_EXTRA_ARCHS_htcwallaby = "arm-oabi"


#Name the generated images in a sane way
IMAGE_NAME = "${DISTRO_NAME}-${IMAGE_BASENAME}-${ANGSTROM_MODE}-${ANGSTROM_PKG_FORMAT}-${DISTRO_VERSION}-${MACHINE}"
DEPLOY_DIR_IMAGE = "${DEPLOY_DIR}/images/${MACHINE}"

# Angstrom *always* has some form of release config, so error out if someone thinks he knows better 
DISTRO_CHECK := "${@bb.data.getVar("DISTRO_VERSION",d,1) or bb.fatal('Remove this line or set a dummy DISTRO_VERSION if you really want to build an unversioned distro')}"


# Configuration for tinderbox, so people only need:
# INHERIT          += "tinderclient"
# in their local.conf 

#Name of the client
TINDER_MACHINE  = "${MACHINE}-${ANGSTROM_MODE}-build-image"

# Which lof file to write to, which tree to report to
TINDER_LOG        = "${TMPDIR}/tinder.log"
TINDER_TREE       = "Angstrom"

# HTTP posting    
TINDER_HOST       ?= "ewi546.ewi.utwente.nl"
TINDER_URL        ?= "/tinderbox"

# Which branch do we build
TINDER_BRANCH     = "org.openembedded.dev"
   
## Clobbing
## 0 for rebuilding everything from scratch
## 1 for incremental builds
TINDER_CLOBBER    = "1"

## Do a report at all
TINDER_REPORT     = "1"

# Configuration for seppuku autobuilder, people only need to put:
# INHERIT += "seppuku"
# SEPPUKU_USER         = "your_bugzilla@user.id"
# SEPPUKU_PASS         = "your_bugzilla_password"
# in their local.conf

# seppuku bbclass
SEPPUKU_LOGIN        = "http://bugs.openembedded.org/query.cgi?"
SEPPUKU_QUERY       = "http://bugs.openembedded.org/buglist.cgi?"
SEPPUKU_NEWREPORT    = "http://bugs.openembedded.org/post_bug.cgi?bug_file_loc=http%3A%2F%2F&version=Angstrom&priority=P2&bug_severity=normal&op_sys=Linux&rep_platform=Other&"
SEPPUKU_ADDCOMMENT   = "http://bugs.openembedded.org/process_bug.cgi?bug_file_loc=http%3A%2F%2F&version=Angstrom&priority=P2&bug_severity=normal&op_sys=Linux&rep_platform=Other&"SEPPUKU_ATTACHMENT = "http://bugs.openembedded.org/attachment.cgi?"
SEPPUKU_AUTOBUILD = "1"

SEPPUKU_PRODUCT      = "Openembedded"
SEPPUKU_COMPONENT    = "build"





# We want images supporting the following features (for task-base)
DISTRO_FEATURES = "nfs smbfs wifi ppp alsa bluetooth ext2 irda pcmcia usbgadget usbhost pci"
DISTRO_FEATURES_nslu2 = "nfs smbfs alsa bluetooth ext2 usbhost pci"
