package com.fslk.hairroomapi.service;

import org.springframework.core.io.Resource;

public interface HairService {
    Resource load(String filename);
}
