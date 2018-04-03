package com.jajahome.model;

import java.util.List;

/**
 *  DIY 列表model
 */
public class DiyListModel {

	/**
	 * set : [{"id":"23","name":"卧室测试","image":{"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581.jpg"}},{"id":"17","name":"简装欧式风格","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_41156_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41157_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_41158_auto.jpg"}},{"id":"7","name":"现代DOB","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40504_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40505_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40506_auto.jpg"}},{"id":"27","name":"镜像功能测试","image":{"small":"","thumb":"","url":""}},{"id":"26","name":"简装欧式风格 副本","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_41326_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41327_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_41328_auto.jpg"}},{"id":"19","name":"测试卧室套装","image":{"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/9ee/9b5/006/e97/75d/847/40587_415.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/9ee/9b5/006/e97/75d/847/40587_415.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/9ee/9b5/006/e97/75d/847/40587_415.jpg"}},{"id":"18","name":"测试随意添加单品客厅套装","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40480_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40481_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40482_auto.jpg"}},{"id":"15","name":"测试书房","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40483_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40484_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40485_auto.jpg"}},{"id":"14","name":"测试餐厅","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40486_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40487_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40488_auto.jpg"}},{"id":"13","name":"卧室套装","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40489_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40490_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40491_auto.jpg"}},{"id":"12","name":"卧室套装","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40492_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40493_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40494_auto.jpg"}},{"id":"10","nam e":"DOB工业风格","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40495_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40496_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40497_auto.jpg"}}]
	 * pagination : {"total":"20","count":2,"limit":12,"offset":1}
	 */

	private DataBean data;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * data : {"set":[{"id":"23","name":"卧室测试","image":{"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581.jpg"}},{"id":"17","name":"简装欧式风格","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_41156_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41157_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_41158_auto.jpg"}},{"id":"7","name":"现代DOB","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40504_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40505_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40506_auto.jpg"}},{"id":"27","name":"镜像功能测试","image":{"small":"","thumb":"","url":""}},{"id":"26","name":"简装欧式风格 副本","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_41326_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41327_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_41328_auto.jpg"}},{"id":"19","name":"测试卧室套装","image":{"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/9ee/9b5/006/e97/75d/847/40587_415.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/9ee/9b5/006/e97/75d/847/40587_415.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/9ee/9b5/006/e97/75d/847/40587_415.jpg"}},{"id":"18","name":"测试随意添加单品客厅套装","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40480_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40481_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40482_auto.jpg"}},{"id":"15","name":"测试书房","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40483_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40484_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40485_auto.jpg"}},{"id":"14","name":"测试餐厅","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40486_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40487_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40488_auto.jpg"}},{"id":"13","name":"卧室套装","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40489_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40490_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40491_auto.jpg"}},{"id":"12","name":"卧室套装","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40492_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40493_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40494_auto.jpg"}},{"id":"10","nam e":"DOB工业风格","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/icon/small_40495_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_40496_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/big_40497_auto.jpg"}}],"pagination":{"total":"20","count":2,"limit":12,"offset":1}}
	 * cmd : setlist_v2
	 * status : 0
	 */

	private String cmd;
	private int status;

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static class DataBean {
		/**
		 * total : 20
		 * count : 2
		 * limit : 12
		 * offset : 1
		 */

		private PaginationBean pagination;
		/**
		 * id : 23
		 * name : 卧室测试
		 * image : {"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581.jpg"}
		 */

		private List<SetBean> set;

		public PaginationBean getPagination() {
			return pagination;
		}

		public void setPagination(PaginationBean pagination) {
			this.pagination = pagination;
		}

		public List<SetBean> getSet() {
			return set;
		}

		public void setSet(List<SetBean> set) {
			this.set = set;
		}

		public static class PaginationBean {
			private String total;
			private int count;
			private int limit;
			private int offset;

			public String getTotal() {
				return total;
			}

			public void setTotal(String total) {
				this.total = total;
			}

			public int getCount() {
				return count;
			}

			public void setCount(int count) {
				this.count = count;
			}

			public int getLimit() {
				return limit;
			}

			public void setLimit(int limit) {
				this.limit = limit;
			}

			public int getOffset() {
				return offset;
			}

			public void setOffset(int offset) {
				this.offset = offset;
			}
		}

		public static class SetBean {
			private String id;
			private String name;
			private String category;
			private String tips;

			public String getCategory() {
				return category;
			}

			public void setCategory(String category) {
				this.category = category;
			}

			public String getTips() {
				return tips;
			}

			public void setTips(String tips) {
				this.tips = tips;
			}

			/**
			 * small : http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581_2.jpg
			 * thumb : http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581_1.jpg
			 * url : http://115.159.102.231/gjj/backend/web/images/newtaozhuang/taozhuangsuolue/d1e/9ba/f57/6e3/140d2bb9846a2129e581.jpg
			 */

			private ImageBean image;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public ImageBean getImage() {
				return image;
			}

			public void setImage(ImageBean image) {
				this.image = image;
			}

			public static class ImageBean {
				private String small;
				private String thumb;
				private String url;

				public String getSmall() {
					return small;
				}

				public void setSmall(String small) {
					this.small = small;
				}

				public String getThumb() {
					return thumb;
				}

				public void setThumb(String thumb) {
					this.thumb = thumb;
				}

				public String getUrl() {
					return url;
				}

				public void setUrl(String url) {
					this.url = url;
				}
			}
		}
	}
}
