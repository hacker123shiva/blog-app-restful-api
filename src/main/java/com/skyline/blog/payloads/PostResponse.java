package com.skyline.blog.payloads;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
 private List<PostDto> content;
 private int pageNumber;
 private int pageSize;
 private long totalElements;
 private int totalpages;
 private boolean lastpage;
}
