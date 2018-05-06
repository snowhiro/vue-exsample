package jp.dip.snowsaber.work.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BpWorkAdminService extends ServiceBase {

	public void find() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from work_time");
		list.forEach(row -> {
			System.out.println(row);
		});
	}
}
