<template>
  <div class="category">
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

      <a-form :model="category" :label-col="{span : 6}" >

        <a-form-item label="名称"
                     :rules="[{ required: true, message: '名称不能为空!' }]"
        >
          <a-input v-model:value="category.name" />
        </a-form-item>
        <a-form-item label="父分类">

            <a-select

                v-model:value="category.parent"
                ref="select"
            >
              <a-select-option :value="0">
              </a-select-option>
              <a-select-option v-for="c in level1"
                               :key="c.id"
                               :value="c.id"
                               :disabled="category.id===c.id">
                {{c.name}}
              </a-select-option>
            </a-select>
        </a-form-item>

        <a-form-item label="顺序">
          <a-input v-model:value="category.sort" type="text"/>
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

export default defineComponent({





  name: 'AdminCategory',
  setup() {
    const categorys = ref();

    const columns = [

      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '父分类',
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
    const category = ref({})

    const param = ref()
    param.value = {};

    const open = ref<boolean>(false);
    const modalLoading = ref(false);
    const handleModelOk = (e: MouseEvent) => {
      console.log(e);



      axios.post("/category/save", category.value
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
     * 编辑
     */

    const edit = (record: any) => {
      category.value = Tool.copy(record);
      open.value = true;

    };
    /**
     * 新增
     * @param record
     */
    const add = () => {

      open.value = true;
      category.value = {};
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
      categorys.value = [];
      axios.get("/category/all"
      ).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          categorys.value = data.content;
          console.log("原始数组: ", categorys.value)

          level1.value = []
          level1.value = Tool.array2Tree(categorys.value, 0);
          console.log("树形数据是：", level1.value )
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
      axios.delete("/category/delete/" + id
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
      category,
      level1,

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

