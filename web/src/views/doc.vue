<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <h3 v-if="level1.length === 0">
        对不起，这本电子书还没有内容
      </h3>
        <a-row>
          <a-col :span="6">
            <a-tree
                v-if="level1.length > 0"
                :tree-data="level1"
                @select="onSelect"
                :replaceFields="{title: 'name', key: 'id', value: 'id'}"
                :defaultExpandAll = "true"
                :defaultSelectedKeys="defaultSelectedKeys"
            />

          </a-col>
          <a-col :span="18">
            <div>
              <h2>
                {{doc.name}}
              </h2>
              <div>
                <span>
                  阅读数: {{doc.viewCount}} &nbsp;&nbsp;
                </span>
                <span>
                  点赞数：{{doc.voteCount}}
                </span>
                <a-divider style="height: 2px; background-color: #7cb305" />
              </div>

            </div>

            <div class="wangeditor"  :innerHTML ="html">
            </div>
            <a-button type="primary" size="large" ghost @click="vote">
              <template #icon><LikeOutlined /></template>
              点赞{{doc.voteCount}}

            </a-button>
            <div>

            </div>
          </a-col>
        </a-row>

    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent, ref,onMounted} from 'vue';
import axios from "axios";
import {Tool} from "@/util/tool";
import {message} from "ant-design-vue";
import {useRoute} from "vue-router";

export default defineComponent({
  setup(){
    const loading = ref(false);
    const docs = ref();
    const level1 = ref();
    level1.value ={}


    const route = useRoute()
    console.log("路由", route);
    console.log("route.path", route.path);
    console.log("route.query", route.query);
    console.log("route.param", route.params);
    console.log("route.fullPath", route.fullPath);
    console.log("route.name", route.name);
    console.log("route.meta", route.meta);
    const html = ref();
    // 当前选中的文档
    const doc = ref();

    doc.value = {
 /*     viewCount : 0,
      voteCount:0*/
    };
    const handleQuery = () => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      docs.value = [];
      axios.get("/doc/all/" + route.query.ebookId
      ).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          docs.value = data.content;
          console.log("原始数组: ", docs.value)

          level1.value = []
          level1.value = Tool.array2Tree(docs.value, 0);


          console.log("树形数据是：", level1.value )
          /*          // 父文档下拉框初始化，相当于点击新增
                    treeSelectData.value = Tool.copy(level1.value) || [];
                    // 为选择树添加一个"无"
                    treeSelectData.value.unshift({id: 0, name: '无'});*/


          if (Tool.isNotEmpty(level1)){
            defaultSelectedKeys.value = [level1.value[0].id];
            handleQueryContent(level1.value[0].id);
            doc.value = level1.value[0];
          }


        } else {
          message.error(data.message);
        }
      });
    };
    /**
     * 内容查询
     **/
    const handleQueryContent = (id: number) => {
      axios.get("/doc/find/" + id).then((response) => {
        const data = response.data;
        if (data.success) {
          html.value = data.content;
        } else {
          message.error(data.message);
        }
      });
    };
    const vote = ()=>{
      axios.get('/doc/vote/' + doc.value.id).then((response) => {
        const  data = response.data;
        if (data.success){
          doc.value.voteCount++;
        }else {
          message.error(data.message)
        }
      })
    }


    const defaultSelectedKeys = ref();
    defaultSelectedKeys.value = [];

    const onSelect = (selectedKeys: any, info: any) => {
      console.log('selected', selectedKeys, info);
      console.log('selectedwwwwwwww222w',  info);
      if (Tool.isNotEmpty(selectedKeys)) {


        // 加载内容
        handleQueryContent(selectedKeys[0]);

        // 选中某一节点时，加载该节点的文档信息
        console.log("++++++++++++++++++", info.selectedNodes[0] )
        doc.value = info.selectedNodes[0];
        console.log("++++++++++++++++++", doc )

      }
    };

    onMounted(()=>{
      handleQuery()
    });
    return {
      level1,
      doc,
      html,
      defaultSelectedKeys,
      handleQuery,
      vote,
      onSelect,
    }



  }
})
</script>

<style>
/* wangeditor默认样式, 参照: http://www.wangeditor.com/doc/pages/02-%E5%86%85%E5%AE%B9%E5%A4%84%E7%90%86/03-%E8%8E%B7%E5%8F%96html.html */
/* table 样式 */
.wangeditor table {
  border-top: 1px solid #ccc;
  border-left: 1px solid #ccc;
}
.wangeditor table td,
.wangeditor table th {
  border-bottom: 1px solid #ccc;
  border-right: 1px solid #ccc;
  padding: 3px 5px;
}
.wangeditor table th {
  border-bottom: 2px solid #ccc;
  text-align: center;
}

/* blockquote 样式 */
.wangeditor blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 5px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #f1f1f1;
}

/* code 样式 */
.wangeditor code {
  display: inline-block;
  *display: inline;
  *zoom: 1;
  background-color: #f1f1f1;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 0 3px;
}
.wangeditor pre code {
  display: block;
}

/* ul ol 样式 */
.wangeditor ul, ol {
  margin: 10px 0 10px 20px;
}

/* 和antdv p冲突，覆盖掉 */
.wangeditor blockquote p {
  font-family:"YouYuan";
  margin: 20px 10px !important;
  font-size: 16px !important;
  font-weight:600;
}

/* 点赞 */
.vote-div {
  padding: 15px;
  text-align: center;
}

/* 图片自适应 */
.wangeditor img {
  max-width: 100%;
  height: auto;
}

/* 视频自适应 */
.wangeditor iframe {
  width: 100%;
  height: 400px;
}
</style>

