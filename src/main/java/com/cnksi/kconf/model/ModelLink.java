package com.cnksi.kconf.model;

import java.util.List;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class ModelLink extends BaseModel<ModelLink> {

	public static final ModelLink me = new ModelLink();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	/**
	 *
	 * @param mid
	 * @param dept_id
	 * @return
	 */
	public List<ModelLink> findModelLinks(Long mid,Long dept_id) {
		String sql = "select * from k_model_link where mid = ? and enabled= 0 and id not in " +
				"(SELECT kid FROM k_model_link_authority a WHERE a.mid = ? and a.enabled=0 GROUP BY kid HAVING COUNT(CASE dept_id WHEN ? THEN TRUE END) = 0)   " +
				"order by ord asc  ";

		return find(sql, mid,mid,dept_id);
	}
}
