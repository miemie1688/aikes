// 文件路径: @/api/admin/cover_path.js

// 导入必要的封装方法，包括新增的 putWithLoadTip 和 deleteWithLoadTip
import { 
  get, 
  postWithLoadTip,
  putWithLoadTip,  // <-- 新增导入
  deleteWithLoadTip // <-- 新增导入
} from '@/utils/request'

/**
 * 管理员端试卷封面路径管理接口
 * 对应后端 AdminExamCoverPathController, 路径前缀: /admin/cover_path
 */
export default {

  /**
   * 1. 新增封面路径 (Create)
   * POST /admin/cover_path
   * @param {Object} data - TExamCoverPath 实例, 包含 examId (Integer) 和 coverPath (String)
   * @returns {Promise<any>}
   */
  addCoverPath: (data) => postWithLoadTip('api/admin/cover_path', data),

  /**
   * 2. 根据试卷ID查询封面路径详情 (Retrieve by ID)
   * GET /admin/cover_path/selectById/{examId}
   * @param {Number} examId - 试卷ID
   * @returns {Promise<TExamCoverPath>}
   */
  getCoverPathById: (examId) => get(`api/admin/cover_path/selectById/${examId}`),

  /**
   * 3. 更新封面路径 (Update)
   * PUT /admin/cover_path
   * @param {Object} data - TExamCoverPath 实例 (包含 examId 和 coverPath)
   * @returns {Promise<any>}
   */
  updateCoverPath: (data) => putWithLoadTip('api/admin/cover_path', data), 

  /**
   * 4. 删除封面路径 (Delete)
   * DELETE /admin/cover_path/{examId}
   * @param {Number} examId - 试卷ID
   * @returns {Promise<any>}
   */
  // DELETE 请求通常不需要 body，第二个参数传 null 或 {}
  deleteCoverPath: (examId) => deleteWithLoadTip(`api/admin/cover_path/${examId}`, null), 

  /**
   * 5. 查询所有封面路径 (Retrieve All)
   * GET /admin/cover_path/all
   * @returns {Promise<Array<TExamCoverPath>>}
   */
  getAllCoverPaths: () => get('api/admin/cover_path/all')
}