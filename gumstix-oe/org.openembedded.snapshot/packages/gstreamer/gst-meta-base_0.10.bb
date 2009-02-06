# Based on its sibling on Poky which is copyright (C) 2006,2007  OpenedHand LTD

DESCRIPTION = "Gstreamer package groups"
DEPENDS = "gstreamer gst-plugins-base gst-plugins-bad gst-plugins-good gst-plugins-ugly"
PR = "r10"

PACKAGES = "\
    gst-meta-base \
    gst-meta-audio \
    gst-meta-debug \
    gst-meta-video"

ALLOW_EMPTY = "1"

RDEPENDS_gst-meta-base = "\
    gstreamer \
    gst-plugin-playbin \
    gst-plugin-decodebin \
    gst-plugin-volume \
    gst-plugin-audioconvert \
    gst-plugin-audioresample \
    gst-plugin-typefindfunctions \
    gst-plugin-autodetect"

RRECOMMENDS_gst-meta-base = "\
    gst-plugin-gnomevfs \
    gst-plugin-alsa \
    gst-plugin-ximagesink \
    gst-plugin-videoscale \
    gst-plugin-videorate \
    gst-plugin-ffmpegcolorspace"

RDEPENDS_gst-meta-audio = "\
    gst-meta-base \
    gst-plugin-ivorbis \
    gst-plugin-ogg \
    gst-plugin-mad \
    gst-plugin-id3demux"

RDEPENDS_gst-meta-debug = "\
    gst-meta-base \
    gst-plugin-debug \
    gst-plugin-audiotestsrc \
    gst-plugin-videotestsrc"

RDEPENDS_gst-meta-video = "\
    gst-meta-base \
    gst-plugin-avi \
    gst-plugin-matroska \
    gst-plugin-mpegstream \
    gst-plugin-mpegaudioparse \
    gst-plugin-mpegvideoparse \
    gst-plugin-mpeg2dec"

RRECOMMENDS_gst-meta-video = "\
    gst-meta-audio"
