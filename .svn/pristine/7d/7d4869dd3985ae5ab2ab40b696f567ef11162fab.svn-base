package com.jajahome.model;

import java.io.Serializable;
import java.util.List;

/**
 *  首页轮播图 数据
 */
public class HomeBannerModel  implements Serializable{

	private DataBean data;
	/**
	 * data : {"banner":[{"id":"1","description":"DOB工业休闲风格客厅","url":"http://baidu.com","image":{"small":"http://115.159.102.231/gjj/backend/web/images/home_page/195.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/home_page/195.jpg","url":"http://115.159.102.231/gjj/backend/web/images/home_page/195.jpg"},"action":"set","action_id":"3"},{"id":"2","description":"现代餐厅","url":"http://www.jajahome.com","image":{"small":"http://115.159.102.231/gjj/backend/web/images/designer/big/204_DOB2_3x.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/designer/big/204_DOB2_3x.jpg","url":"http://115.159.102.231/gjj/backend/web/images/designer/big/204_DOB2_3x.jpg"},"action":"set","action_id":"13"},{"id":"4","description":"现代客厅","url":"http://www.baidu.com","image":{"small":"http://115.159.102.231/gjj/backend/web/images/home_page/banner/thumb/258_110.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/home_page/banner/thumb/258_110.jpg","url":"http://115.159.102.231/gjj/backend/web/images/designer/big/206_DOB1_3x.jpg"},"action":"","action_id":"0"},{"id":"3","description":"现代卫生间","url":"http://wenda.haosou.com","image":{"small":"http://115.159.102.231/gjj/backend/web/images/home_page/banner/thumb/259_110.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/home_page/banner/thumb/259_110.jpg","url":"http://115.159.102.231/gjj/backend/web/images/designer/big/205_DOB3_3x.jpg"},"action":"set","action_id":"7"}]}
	 * cmd : home
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

	public static class DataBean implements Serializable {
		/**
		 * id : 1
		 * description : DOB工业休闲风格客厅
		 * url : http://baidu.com
		 * image : {"small":"http://115.159.102.231/gjj/backend/web/images/home_page/195.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/home_page/195.jpg","url":"http://115.159.102.231/gjj/backend/web/images/home_page/195.jpg"}
		 * action : set
		 * action_id : 3
		 */

		private List<BannerBean> banner;

		public List<BannerBean> getBanner() {
			return banner;
		}

		public void setBanner(List<BannerBean> banner) {
			this.banner = banner;
		}

		public static class BannerBean implements Serializable {
			private String id;
			private String description;
			private String url;
			/**
			 * small : http://115.159.102.231/gjj/backend/web/images/home_page/195.jpg
			 * thumb : http://115.159.102.231/gjj/backend/web/images/home_page/195.jpg
			 * url : http://115.159.102.231/gjj/backend/web/images/home_page/195.jpg
			 */

			private ImageBean image;
			private String action;
			private String action_id;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public ImageBean getImage() {
				return image;
			}

			public void setImage(ImageBean image) {
				this.image = image;
			}

			public String getAction() {
				return action;
			}

			public void setAction(String action) {
				this.action = action;
			}

			public String getAction_id() {
				return action_id;
			}

			public void setAction_id(String action_id) {
				this.action_id = action_id;
			}

			public static class ImageBean implements Serializable{
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