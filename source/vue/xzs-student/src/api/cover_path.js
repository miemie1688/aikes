import { get } from '@/utils/request' // Adjust this path to your actual request utility file

const base_url = '/api/student/cover_path'

/**
 * 根据试卷ID查询封面路径信息
 * GET /api/student/cover_path/select/{examId}
 * 对应 Java Controller: getCoverPathForStudent
 * @param {number} examId 试卷ID
 * @returns {Promise<Object>} 包含封面路径信息的对象 (TExamCoverPath)
 */
export function getCoverPathById (examId) {
  return get(`${base_url}/select/${examId}`, null)
}

/**
 * 查询所有试卷封面路径信息
 * GET /api/student/cover_path/all
 * 对应 Java Controller: getAllCoverPaths
 * @returns {Promise<Array<Object>>} 包含所有封面路径对象的数组 (List<TExamCoverPath>)
 */
export function getAllCoverPaths () {
  return get(`${base_url}/all`, null)
}
