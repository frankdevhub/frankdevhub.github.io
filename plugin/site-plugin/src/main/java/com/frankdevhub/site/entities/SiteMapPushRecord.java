package com.frankdevhub.site.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("all")
@Table(name = "site_map_push_record")
public class SiteMapPushRecord extends BaseRecord<SiteMapPushRecord> {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "record_id")
	private Long recordId;

}
