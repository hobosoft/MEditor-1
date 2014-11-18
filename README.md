# Metadata Editor [![Build Status](https://travis-ci.org/moravianlibrary/MEditor.svg?branch=master)](https://travis-ci.org/moravianlibrary/MEditor)

RIA web application developed for purposes of digitization of old cultural assets. For more information continue to our [wiki](http://code.google.com/p/meta-editor/wiki/SideBar?tm=6).


## Functionality

Editor is capable of editing following metadata standards:

 * DublinCore;
 * Mods;
 * stuctural metadata stored in Fedora's native format (FOXML) or METS in the RELS-EXT stream. For more information about Fedora digital object, please visit [Fedora Digital Object Model](https://wiki.duraspace.org/display/FEDORA35/Fedora+Digital+Object+Model).

Besides editing the existing digital objects it can also ingest new content into Fedora repository. It converts images (scans) from input directory into Jpeg2000 and stores them in the integrated image server (Djatoka) or copies them into preset location (external image server).
