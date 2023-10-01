<template>
  <div class="doc">
    <a-layout>

      <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >

        <p>
          <a-form
              layout="inline"
              :model="param"
          >
            <a-form-item>
              <a-input v-model:value="param.name" placeholder="名称">
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" danger @click="handleQuery()"
              >
                查询
              </a-button>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" danger @click="add()"  >
                新增
              </a-button>
            </a-form-item>

          </a-form>




        </p>
        <br>
          <a-table
              :columns="columns"
              :row-key="record => record.id"
              :data-source="level1"
              :pagination="false"
              :loading="loading"
          >



<!--             eslint-disable-next-line-->
            <template v-slot:action="{ record,text }">
              <a-space size="small">
                <a-button type="primary" @click="edit(record)" >
                  编辑

                </a-button>

                <a-popconfirm
                    title="删除后不可回复，您确认要删除?"
                    ok-text="Yes"
                    cancel-text="No"
                    @confirm="handleDelete(record.id)"

                >
                  <a-button type="danger" >
                    删除
                  </a-button>
                </a-popconfirm>

              </a-space>
            </template>
          </a-table>
      </a-layout-content>
    </a-layout>

    <a-modal v-model:open="open"
             title="分级"
             @ok="handleModelOk">

      <a-form :model="doc" :label-col="{span : 6}" >

        <a-form-item label="名称"
                     :rules="[{ required: true, message: '名称不能为空!' }]"
        >
          <a-input v-model:value="doc.name" />
        </a-form-item>
        <a-form-item label="父文档"
        >
          <a-tree-select
              v-model:value="doc.parent"
              style="width: 100%"
              :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
              placeholder="Please select"
              :tree-data="treeSelectData"
              tree-default-expand-all
              :fieldNames="{
              label:'name',
              children: 'children',
              value: 'id',
              }"
              tree-node-filter-prop="title"
          >

          </a-tree-select>
        </a-form-item>
<!--        <a-form-item label="父文档">

            <a-select

                v-model:value="doc.parent"
                ref="select"
            >
              <a-select-option :value="0">
              </a-select-option>
              <a-select-option v-for="c in level1"
                               :key="c.id"
                               :value="c.id"
                               :disabled="doc.id===c.id">
                {{c.name}}
              </a-select-option>
            </a-select>
        </a-form-item>-->

        <a-form-item label="顺序">
          <a-input v-model:value="doc.sort" type="text"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>


</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";

export default defineComponent({





  name: 'AdminCategory',
  setup() {
    const docs = ref();
    const route = useRoute()
    console.log("路由", route);
    console.log("route.path", route.path);
    console.log("route.query", route.query);
    console.log("route.param", route.params);
    console.log("route.fullPath", route.fullPath);
    console.log("route.name", route.name);
    console.log("route.meta", route.meta);
    const columns = [

      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '父文档',
        key: 'parent',
        dataIndex: 'parent'
      },
      {
        title: '顺序',
        key: 'sort',
        dataIndex: 'sort'
      },
      {
        title: 'Action',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];

    /**
     * 表单
      */

    const loading = ref(false);

    // const queryName = ref();
    const doc = ref({})

    const param = ref()
    param.value = {};
    //因为树选择组件的属性状态，会随当前编辑的节点变换而变化，所以单独声明一个响应式变量
    const treeSelectData = ref();
    treeSelectData.value = []
    const open = ref<boolean>(false);
    const modalLoading = ref(false);
    const handleModelOk = (e: MouseEvent) => {
      console.log(e);


      level1.value=[]
      axios.post("/doc/save", doc.value
        ).then((response) => {
        modalLoading.value = false;
        const data = response.data;
        if (data.success) {
          open.value=false;
          modalLoading.value = true;
          handleQuery()
        }else {
          message.error(data.message);
        }
      });

    }
    /**
     * 将某节点及其子孙节点全部置为disabled
     */
    const setDisable = (treeSelectData: any, id: any) => {
      // console.log(treeSelectData, id);
      // 遍历数组，即遍历某一层节点
      for (let i = 0; i < treeSelectData.length; i++) {
        const node = treeSelectData[i];
        if (node.id === id) {
          // 如果当前节点就是目标节点
          console.log("disabled", node);
          // 将目标节点设置为disabled
          node.disabled = true;

          // 遍历所有子节点，将所有子节点全部都加上disabled
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              setDisable(children, children[j].id)
            }
          }
        } else {
          // 如果当前节点不是目标节点，则到其子节点再找找看。
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            setDisable(children, id);
          }
        }
      }
    };
    /**
     * 编辑
     */

    const edit = (record: any) => {
      doc.value = Tool.copy(record);
      open.value = true;
      // 不能选择当前节点及其所有子孙节点，作为父节点，会使树断开
      treeSelectData.value = Tool.copy(level1.value);
      //不能选择当前节点，或者子孙节点为父节点,会使用书断开
      setDisable(treeSelectData.value, record.id);


      //为选择树添加一个“无”
      treeSelectData.value.unshift({id:0, name:"无"})
    };
    /**
     * 新增
     * @param record
     */
    const add = () => {

      open.value = true;
      doc.value = {
        ebookId : route.query.ebookId
      };

      treeSelectData.value = Tool.copy(level1.value);
      //为选择树添加一个“无”
      treeSelectData.value.unshift({id:0, name:"无"})
    };
    /**
     * 数据查询
     **/

    /**
     * 树形结构 level1
     * [{
     * id: "",
     * name: "",
     * children:[{
     *   id: "",
     *   name: ""
     * }]
     *
     * }]
     */
    const level1 = ref()
    level1.value = []

    const handleQuery = () => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      docs.value = [];
      axios.get("/doc/all"
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
        } else {
          message.error(data.message);
        }
      });
    };

    /**
     * 删除
     * @param id
     */
    const handleDelete = ( id: number)=>{
      loading.value = true;
      console.log(id);
      axios.delete("/doc/delete/" + id
      ).then((response) => {
        const data = response.data;
        if (data.success) {

          handleQuery()
        }
      });
    }


  onMounted(()=>{
    handleQuery()
  });
    return {

      param,
      // categorys,
      columns,
      loading,
      doc,
      level1,
      treeSelectData,

      edit,
      add,

      modalLoading,
      handleModelOk,
      handleQuery,
      handleDelete,

      open
    }
  }
});
</script>

<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>

