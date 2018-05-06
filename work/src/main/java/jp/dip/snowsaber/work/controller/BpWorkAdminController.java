package jp.dip.snowsaber.work.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.dip.snowsaber.work.service.BpWorkAdminService;

@RestController
public class BpWorkAdminController {

	private static final String BASE_URL = "bpWorkAdmin";

	@Autowired
	private BpWorkAdminService servie;

	@RequestMapping(value = BASE_URL + "/findList", method = RequestMethod.POST)
	public BpWorkAdminIndex findList() {
		List<BpWrokAdmin> list = new ArrayList<>();
		for (int i = 1; i <= 30; i++) {
			BpWrokAdmin bwa = new BpWrokAdmin();
			Calendar cal = Calendar.getInstance();
			cal.set(2018, 4, i);

			bwa.setDay(cal.getTimeInMillis());
			list.add(bwa);
		}
		BpWorkAdminIndex result = new BpWorkAdminIndex();
		result.setMonth("4");
		result.setList(list);

		servie.find();

		return result;
	}

	public static class BpWorkAdminIndex {
		private String month;

		private List<BpWrokAdmin> list;

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public List<BpWrokAdmin> getList() {
			return list;
		}

		public void setList(List<BpWrokAdmin> list) {
			this.list = list;
		}


	}

	public static class BpWrokAdmin {

		public long getDayTime() {
			return dayTime;
		}

		public void setDay(long dayTime) {
			this.dayTime = dayTime;
		}

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = end;
		}

		public String getBreakTime() {
			return breakTime;
		}

		public void setBreakTime(String breakTime) {
			this.breakTime = breakTime;
		}

		public String getWorkTime() {
			return workTime;
		}

		public void setWorkTime(String workTime) {
			this.workTime = workTime;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		private Long dayTime;

		private String start;

		private String end;

		private String breakTime;

		private String workTime;

		private String note;

	}
}
