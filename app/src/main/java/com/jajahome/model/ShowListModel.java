package com.jajahome.model;

import java.util.List;

/**
 * 秀家列表model
 */
public class ShowListModel {

	/**
	 * item : [{"id":"5","name":"测试秀家图","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg"},"type":"0"},{"id":"4","name":"测试秀家床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/a99/6f6/714/11f/9eb/3ab/39328_200.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/a99/6f6/714/11f/9eb/3ab/39328_200.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/a99/6f6/714/11f/9eb/3ab/39328_200.jpg"},"type":"0"},{"id":"27","name":"格式地方","image":{"small":"","thumb":"","url":""},"type":"1"},{"id":"26","name":"test","image":{"small":"","thumb":"","url":""},"type":"1"},{"id":"18","name":"新建秀家图","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41339_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41339_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41339_auto.jpg"},"type":"0"},{"id":"16","name":"测试秀家的图片是否带本地路径","image":{"small":"http://115.159.102.231/gjj/backend/web/images/a31/7bb/1db/bdc/2d3bee3d31b5ccb817ae_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/a31/7bb/1db/bdc/2d3bee3d31b5ccb817ae_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/a31/7bb/1db/bdc/2d3bee3d31b5ccb817ae.jpg"},"type":"1"},{"id":"15","name":"创建秀家","image":{"small":"http://115.159.102.231/gjj/backend/web/images/xiujia/bisai/55e/a92/e36/51b/050/151/39384_493.png","thumb":"http://115.159.102.231/gjj/backend/web/images/xiujia/bisai/55e/a92/e36/51b/050/151/39384_493.png","url":"http://115.159.102.231/gjj/backend/web/images/xiujia/bisai/55e/a92/e36/51b/050/151/39384_493.png"},"type":"1"},{"id":"14","name":"测试插入图片的链接路径如果只是一个固定","image":{"small":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993.jpg","url":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993.jpg"},"type":"1"},{"id":"13","name":"2016年3月份活动公告","image":{"small":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993.jpg"},"type":"3"},{"id":"12","name":"测试","image":{"small":"http://115.159.102.231/gjj/backend/web/images/00a/55c/eda/999/5a89ddbe69957ff0fa77_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/00a/55c/eda/999/5a89ddbe69957ff0fa77_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/00a/55c/eda/999/5a89ddbe69957ff0fa77.jpg"},"type":"1"},{"id":"2","name":"新建秀家图","image":{"small":"http://115.159.102.231/gjj/backend/web/images/table/409_135.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/table/409_135.jpg","url":"http://115.159.102.231/gjj/backend/web/images/table/409_135.jpg"},"type":"0"},{"id":"1","name":"新建秀家图 dt","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/164.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/164.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/164.jpg"},"type":"0"}]
	 * pagination : {"total":"18","count":2,"limit":12,"offset":1}
	 */

	private DataBean data;
	/**
	 * data : {"item":[{"id":"5","name":"测试秀家图","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg"},"type":"0"},{"id":"4","name":"测试秀家床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/a99/6f6/714/11f/9eb/3ab/39328_200.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/a99/6f6/714/11f/9eb/3ab/39328_200.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/a99/6f6/714/11f/9eb/3ab/39328_200.jpg"},"type":"0"},{"id":"27","name":"格式地方","image":{"small":"","thumb":"","url":""},"type":"1"},{"id":"26","name":"test","image":{"small":"","thumb":"","url":""},"type":"1"},{"id":"18","name":"新建秀家图","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41339_auto.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41339_auto.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/thumb/thumb_41339_auto.jpg"},"type":"0"},{"id":"16","name":"测试秀家的图片是否带本地路径","image":{"small":"http://115.159.102.231/gjj/backend/web/images/a31/7bb/1db/bdc/2d3bee3d31b5ccb817ae_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/a31/7bb/1db/bdc/2d3bee3d31b5ccb817ae_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/a31/7bb/1db/bdc/2d3bee3d31b5ccb817ae.jpg"},"type":"1"},{"id":"15","name":"创建秀家","image":{"small":"http://115.159.102.231/gjj/backend/web/images/xiujia/bisai/55e/a92/e36/51b/050/151/39384_493.png","thumb":"http://115.159.102.231/gjj/backend/web/images/xiujia/bisai/55e/a92/e36/51b/050/151/39384_493.png","url":"http://115.159.102.231/gjj/backend/web/images/xiujia/bisai/55e/a92/e36/51b/050/151/39384_493.png"},"type":"1"},{"id":"14","name":"测试插入图片的链接路径如果只是一个固定","image":{"small":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993.jpg","url":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993.jpg"},"type":"1"},{"id":"13","name":"2016年3月份活动公告","image":{"small":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/761/0bb/1c2/1f2/8d90a5ec72d2ac2d5993.jpg"},"type":"3"},{"id":"12","name":"测试","image":{"small":"http://115.159.102.231/gjj/backend/web/images/00a/55c/eda/999/5a89ddbe69957ff0fa77_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/00a/55c/eda/999/5a89ddbe69957ff0fa77_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/00a/55c/eda/999/5a89ddbe69957ff0fa77.jpg"},"type":"1"},{"id":"2","name":"新建秀家图","image":{"small":"http://115.159.102.231/gjj/backend/web/images/table/409_135.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/table/409_135.jpg","url":"http://115.159.102.231/gjj/backend/web/images/table/409_135.jpg"},"type":"0"},{"id":"1","name":"新建秀家图 dt","image":{"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/164.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/164.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/164.jpg"},"type":"0"}],"pagination":{"total":"18","count":2,"limit":12,"offset":1}}
	 * cmd : showlist
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
		 * total : 18
		 * count : 2
		 * limit : 12
		 * offset : 1
		 */

		private PaginationBean pagination;
		/**
		 * id : 5
		 * name : 测试秀家图
		 * image : {"small":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg","url":"http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg"}
		 * type : 0
		 */

		private List<ItemBean> item;

		public PaginationBean getPagination() {
			return pagination;
		}

		public void setPagination(PaginationBean pagination) {
			this.pagination = pagination;
		}

		public List<ItemBean> getItem() {
			return item;
		}

		public void setItem(List<ItemBean> item) {
			this.item = item;
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

		public static class ItemBean {
			private String id;
			private String name;
			/**
			 * small : http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg
			 * thumb : http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg
			 * url : http://115.159.102.231/gjj/backend/web/images/taozhuang/big/13338_102.jpg
			 */

			private ImageBean image;
			private String type;

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

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
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
