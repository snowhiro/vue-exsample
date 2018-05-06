package jp.dip.snowsaber.work.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * サービス既定クラス
 *
 * @author snowhiro
 *
 */
public abstract class ServiceBase {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	/** キャッシュ用 */
	private Map<String, String> sqlCache = new HashMap<>();

	public List<Map<String, Object>> findList(String sqlFileName) {
		String sql = getSql(getSqlFile(sqlFileName), null);
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * SQLファイル名を取得
	 *
	 * @param sqlFileName sqlファイル名(クラス名および.sqlを除く部分)
	 * @return sqlファイル名
	 *
	 * */
	private String getSqlFile(String sqlFileName) {
		String name = this.getClass().getSimpleName();
		return name + "_" + sqlFileName +".sql";
	}

	/**
	 * SQLを取得
	 * @param sqlFileName SQLファイル名
	 * @param ob 引数オブェクト
	 * @return 発行用のSQL
	 */
	private String getSql(String sqlFileName, Object ob) {
		StringBuilder sb = new StringBuilder();
		try {
			String sql = getSqlBaseBody(sqlFileName);
			sb.append(sql);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return sb.toString();
	}

	/**
	 * SQLファイルをテキストファイルより取得
	 *
	 * */
	private String getSqlBaseBody(String sqlFileName) throws IOException {
		if (sqlCache.containsKey(sqlFileName)) {
			return sqlCache.get(sqlFileName);
		} else {
			File sqlFile = new File(getClass().getResource(sqlFileName).getFile());
			return String.join("", Files.readAllLines(sqlFile.toPath()));
		}
	}
}
